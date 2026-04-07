package com.hoehmann.salesbot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.hoehmann.salesbot.data.ServiceItem
import com.hoehmann.salesbot.data.ServicesRepository
import com.hoehmann.salesbot.databinding.ActivityMainBinding
import com.hoehmann.salesbot.robot.TemiRobotManager
import com.hoehmann.salesbot.ui.ServiceAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        private const val INACTIVITY_TIMEOUT_MS = 60_000L // 60 seconds
    }

    private lateinit var binding: ActivityMainBinding
    private val robotManager = TemiRobotManager()

    private val inactivityHandler = Handler(Looper.getMainLooper())

    private val inactivityRunnable = Runnable {
        showMainMenu()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        robotManager.initialize()

        setupButtonListeners()
        showMainMenu()

        // Greet customer after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            robotManager.greetCustomer()
        }, 2000)

        resetInactivityTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        robotManager.cleanup()
        inactivityHandler.removeCallbacks(inactivityRunnable)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        resetInactivityTimer()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        resetInactivityTimer()
        return super.dispatchTouchEvent(ev)
    }

    private fun resetInactivityTimer() {
        inactivityHandler.removeCallbacks(inactivityRunnable)
        inactivityHandler.postDelayed(inactivityRunnable, INACTIVITY_TIMEOUT_MS)
    }

    private fun setupButtonListeners() {
        binding.buttonBack.setOnClickListener {
            showMainMenu()
        }
        binding.buttonContact.setOnClickListener {
            showContactForm()
        }
        binding.buttonPatrol.setOnClickListener {
            startPatrolMode()
        }
    }

    private fun showMainMenu() {
        binding.recyclerServices.visibility = View.VISIBLE
        binding.scrollDetail.visibility = View.GONE
        binding.scrollContact.visibility = View.GONE
        binding.buttonBack.visibility = View.GONE
        binding.buttonContact.visibility = View.VISIBLE
        binding.buttonPatrol.visibility = View.VISIBLE

        val services = ServicesRepository.getAllServices()
        val adapter = ServiceAdapter(services) { service ->
            showServiceDetail(service)
        }
        binding.recyclerServices.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerServices.adapter = adapter
    }

    private fun showServiceDetail(service: ServiceItem) {
        binding.recyclerServices.visibility = View.GONE
        binding.scrollDetail.visibility = View.VISIBLE
        binding.scrollContact.visibility = View.GONE
        binding.buttonBack.visibility = View.VISIBLE
        binding.buttonContact.visibility = View.VISIBLE
        binding.buttonPatrol.visibility = View.GONE

        binding.textDetailIcon.text = service.icon
        binding.textDetailTitle.text = service.title
        binding.textDetailSubtitle.text = service.subtitle
        binding.textDetailDescription.text = service.description

        // Build highlights list
        val highlightsText = service.highlights.joinToString("\n") { "✅ $it" }
        binding.textDetailHighlights.text = highlightsText

        // Navigation button
        if (service.location != null) {
            binding.buttonNavigate.visibility = View.VISIBLE
            binding.buttonNavigate.setOnClickListener {
                robotManager.navigateTo(service.location)
            }
        } else {
            binding.buttonNavigate.visibility = View.GONE
        }

        // Consultation button
        binding.buttonConsultation.setOnClickListener {
            showContactForm(subject = service.title)
        }

        robotManager.speakServiceInfo(service.id)
    }

    private fun showContactForm(subject: String? = null) {
        binding.recyclerServices.visibility = View.GONE
        binding.scrollDetail.visibility = View.GONE
        binding.scrollContact.visibility = View.VISIBLE
        binding.buttonBack.visibility = View.VISIBLE
        binding.buttonContact.visibility = View.GONE
        binding.buttonPatrol.visibility = View.GONE

        subject?.let {
            binding.editSubject.setText(it)
        }

        binding.buttonSubmit.setOnClickListener {
            submitContactForm()
        }
    }

    private fun submitContactForm() {
        val name = binding.editName.text.toString()
        val email = binding.editEmail.text.toString()
        val phone = binding.editPhone.text.toString()
        val subject = binding.editSubject.text.toString()

        // TODO: Send contact data to CRM (e.g. HubSpot or Salesforce) via REST API.
        // Required fields: name, email, phone, subject. Integration point to be configured
        // with the customer's preferred CRM system.
        robotManager.speak(
            "Vielen Dank, $name! Wir haben Ihre Anfrage erhalten und melden uns schnellstmöglich bei Ihnen. " +
            "Haben Sie noch weitere Fragen?"
        )

        // Clear form
        binding.editName.setText("")
        binding.editEmail.setText("")
        binding.editPhone.setText("")
        binding.editSubject.setText("")

        // Return to main menu after a short delay
        Handler(Looper.getMainLooper()).postDelayed({
            showMainMenu()
        }, 3000)
    }

    private fun startPatrolMode() {
        val locations = listOf(
            TemiRobotManager.ENTRANCE,
            TemiRobotManager.IT_SERVICES,
            TemiRobotManager.TELEPHONES,
            TemiRobotManager.HARDWARE,
            TemiRobotManager.CHECKOUT
        )
        robotManager.startPatrol(locations)
        robotManager.speak("Ich starte jetzt meinen Rundgang durch das Geschäft. Folgen Sie mir!")
    }
}
