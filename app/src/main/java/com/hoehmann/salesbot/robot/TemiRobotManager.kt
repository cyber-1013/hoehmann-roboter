package com.hoehmann.salesbot.robot

import android.util.Log
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener
import com.robotemi.sdk.listeners.OnRobotReadyListener
import com.robotemi.sdk.navigation.model.SpeedLevel

class TemiRobotManager : OnRobotReadyListener, OnGoToLocationStatusChangedListener {

    companion object {
        private const val TAG = "TemiRobotManager"

        // Location constants
        const val ENTRANCE = "Eingang"
        const val IT_SERVICES = "IT-Services"
        const val TELEPHONES = "Telefonanlagen"
        const val HARDWARE = "Hardware"
        const val CHECKOUT = "Kasse"
        const val HOME_BASE = "home base"
    }

    private var robot: Robot? = null
    private var patrolLocations: List<String> = emptyList()
    private var patrolIndex: Int = 0
    private var isPatrolling: Boolean = false
    private var navigationCallback: ((String, String) -> Unit)? = null

    fun initialize() {
        robot = Robot.getInstance()
        robot?.addOnRobotReadyListener(this)
        robot?.addOnGoToLocationStatusChangedListener(this)
        Log.d(TAG, "TemiRobotManager initialized")
    }

    fun cleanup() {
        robot?.removeOnRobotReadyListener(this)
        robot?.removeOnGoToLocationStatusChangedListener(this)
        robot = null
        Log.d(TAG, "TemiRobotManager cleaned up")
    }

    // OnRobotReadyListener
    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            robot?.hideTopBar()
            Log.d(TAG, "Robot is ready, top bar hidden")
        }
    }

    // TTS methods
    fun speak(text: String, showOnScreen: Boolean = true) {
        val ttsRequest = TtsRequest.create(
            speech = text,
            language = TtsRequest.Language.DE_DE,
            showAnimations = showOnScreen
        )
        robot?.speak(ttsRequest)
        Log.d(TAG, "Speaking: $text")
    }

    fun greetCustomer() {
        speak(
            "Herzlich willkommen bei Hoehmann IT und Kommunikation! " +
            "Ich bin Ihr digitaler Berater. " +
            "Tippen Sie auf den Bildschirm, um unsere Leistungen zu entdecken, " +
            "oder sagen Sie mir, wie ich Ihnen helfen kann!"
        )
    }

    fun speakServiceInfo(serviceId: String) {
        val text = when (serviceId) {
            "it_services" ->
                "Wir bieten IT Pakete zum Festpreis. " +
                "Transparent und ohne versteckte Kosten. " +
                "Mit unserem 24 Stunden Notfallservice sind Sie immer abgesichert."
            "telephones" ->
                "Als STARFACE Partner bieten wir Ihnen moderne VoIP Telefonanlagen. " +
                "Wir unterstützen Sie bei der Digitalisierung Ihrer Telefonanschlüsse."
            "cloud" ->
                "Wir helfen Ihnen beim Wechsel in die Cloud. " +
                "Cloudbasierte IT Lösungen sparen Zeit und Kosten."
            "security" ->
                "Als Securepoint Premium Partner schützen wir Ihr Unternehmen vor Datenverlust und Cyberangriffen. " +
                "Fragen Sie nach unserem kostenlosen IT Sicherheits Check!"
            "hardware" ->
                "Wir verkaufen und richten Computer, Laptops und Telefone für Sie ein. " +
                "Alles aus einer Hand."
            "datenschutz" ->
                "Wir beraten Sie umfassend zum Thema Datenschutz und sind als EDV Sachverständige für Sie da."
            else -> "Bitte wählen Sie eine unserer Leistungen aus."
        }
        speak(text)
    }

    // Navigation methods
    fun navigateTo(location: String, onComplete: (() -> Unit)? = null) {
        navigationCallback = { loc, status ->
            if (loc == location && status == OnGoToLocationStatusChangedListener.COMPLETE) {
                onComplete?.invoke()
            }
        }
        speak("Ich bringe Sie jetzt zu $location")
        robot?.goTo(location, SpeedLevel.MEDIUM)
        Log.d(TAG, "Navigating to: $location")
    }

    fun goHome() {
        isPatrolling = false
        robot?.goTo(HOME_BASE, SpeedLevel.MEDIUM)
        Log.d(TAG, "Going home")
    }

    fun startPatrol(locations: List<String>) {
        if (locations.isEmpty()) return
        isPatrolling = true
        patrolLocations = locations
        patrolIndex = 0
        navigateToNextPatrolLocation()
    }

    private fun navigateToNextPatrolLocation() {
        if (!isPatrolling || patrolLocations.isEmpty()) return
        val location = patrolLocations[patrolIndex]
        robot?.goTo(location, SpeedLevel.MEDIUM)
        Log.d(TAG, "Patrol: navigating to $location")
    }

    // OnGoToLocationStatusChangedListener
    override fun onGoToLocationStatusChanged(
        location: String,
        status: String,
        descriptionId: Int,
        description: String
    ) {
        Log.d(TAG, "Location status changed: $location -> $status")
        navigationCallback?.invoke(location, status)

        if (isPatrolling && status == OnGoToLocationStatusChangedListener.COMPLETE) {
            patrolIndex = (patrolIndex + 1) % patrolLocations.size
            navigateToNextPatrolLocation()
        }
    }

    // Helper methods
    fun getLocations(): List<String> {
        return robot?.locations ?: emptyList()
    }

    fun turnToUser() {
        robot?.tiltAngle(0)
        Log.d(TAG, "Turning to user")
    }

    fun setVolume(level: Int) {
        robot?.setVolume(level)
        Log.d(TAG, "Volume set to: $level")
    }
}
