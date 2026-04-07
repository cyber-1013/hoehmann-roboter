package com.hoehmann.salesbot.data

import com.hoehmann.salesbot.robot.TemiRobotManager

object ServicesRepository {

    fun getAllServices(): List<ServiceItem> = listOf(
        ServiceItem(
            id = "it_services",
            title = "IT-Services",
            subtitle = "IT-Pakete zum Festpreis",
            description = "Wir bieten maßgeschneiderte IT-Pakete zum Festpreis – transparent und ohne versteckte Kosten. " +
                "Mit unserem 24-Stunden-Notfallservice sind Sie immer abgesichert.",
            icon = "🖥️",
            highlights = listOf(
                "IT-Pakete zum Festpreis",
                "24/7 Notfallservice",
                "Remote Management & Fernwartung",
                "Serververwaltung & Standortvernetzung",
                "Prozessoptimierung"
            ),
            location = TemiRobotManager.IT_SERVICES
        ),
        ServiceItem(
            id = "telephones",
            title = "Telefonanlagen",
            subtitle = "STARFACE VoIP-Telefonanlagen",
            description = "Als STARFACE Partner bieten wir Ihnen moderne VoIP-Telefonanlagen. " +
                "Wir unterstützen Sie bei der vollständigen Digitalisierung Ihrer Telefonanschlüsse.",
            icon = "📞",
            highlights = listOf(
                "STARFACE VoIP-Telefonanlagen",
                "Digitalisierung der Telefonanschlüsse",
                "Beratung & Installation",
                "Wartung & Support"
            ),
            location = TemiRobotManager.TELEPHONES
        ),
        ServiceItem(
            id = "cloud",
            title = "Cloud-Lösungen",
            subtitle = "Flexibel in die Cloud",
            description = "Wir helfen Ihnen beim Wechsel in die Cloud. " +
                "Cloudbasierte IT-Lösungen sparen Zeit und Kosten und bieten maximale Flexibilität.",
            icon = "☁️",
            highlights = listOf(
                "Cloud-Migration",
                "Microsoft 365 & Azure",
                "Backup-Lösungen",
                "Flexible Skalierung"
            ),
            location = null
        ),
        ServiceItem(
            id = "security",
            title = "IT-Sicherheit",
            subtitle = "Securepoint Premium Partner",
            description = "Als Securepoint Premium Partner schützen wir Ihr Unternehmen vor Datenverlust und Cyberangriffen. " +
                "Fragen Sie nach unserem kostenlosen IT-Sicherheits-Check!",
            icon = "🔒",
            highlights = listOf(
                "Securepoint Premium Partner",
                "IT-Sicherheits-Check",
                "Firewalls & Virenschutz",
                "E-Mail-Sicherheit",
                "Mitarbeiterschulungen"
            ),
            location = null
        ),
        ServiceItem(
            id = "hardware",
            title = "Hard- & Software",
            subtitle = "Alles aus einer Hand",
            description = "Wir verkaufen und richten Computer, Laptops und Telefone für Sie ein. " +
                "Alles aus einer Hand – von der Beratung bis zur Einrichtung.",
            icon = "💻",
            highlights = listOf(
                "Computer & Laptops",
                "Monitore & Peripherie",
                "Telefone & Headsets",
                "Einrichtung & Konfiguration",
                "Garantie & Support"
            ),
            location = TemiRobotManager.HARDWARE
        ),
        ServiceItem(
            id = "datenschutz",
            title = "Datenschutz",
            subtitle = "DSGVO-konform & sicher",
            description = "Wir beraten Sie umfassend zum Thema Datenschutz und sind als EDV-Sachverständige für Sie da. " +
                "Ihr Unternehmen bleibt DSGVO-konform.",
            icon = "📋",
            highlights = listOf(
                "DSGVO-Beratung",
                "Datenschutzkonzepte",
                "EDV-Sachverständiger",
                "Mitarbeiterschulungen"
            ),
            location = null
        )
    )

    fun getServiceById(id: String): ServiceItem? = getAllServices().find { it.id == id }
}
