# 🤖 Hoehmann IT Berater – Temi Verkaufsroboter App

Eine Android-App für den **Temi-Roboter** als **Verkaufsroboter** der Firma [Hoehmann IT & Kommunikation](https://it-hoehmann.com/) aus Tuttlingen.

---

## ✨ Features

- **Interaktives Service-Menü** mit 6 Leistungsbereichen im Grid-Layout
- **Temi TTS** – Sprachausgabe auf Deutsch (DE_DE) für Begrüßung und Service-Infos
- **Navigation** – Roboter navigiert zu definierten Standorten im Raum
- **Patrol-Modus** – Autonomer Rundgang durch alle Stationen
- **Kontaktformular** – Kundenanfragen direkt am Roboter erfassen (CRM-Integration vorbereitet)
- **Kiosk-Modus** – App startet automatisch mit dem Temi-Roboter
- **Inaktivitäts-Timer** – Automatische Rückkehr zum Hauptmenü nach 60 Sekunden
- **Begrüßung** – Automatische Willkommensnachricht 2 Sekunden nach App-Start

---

## 🛠️ Voraussetzungen

- **Android Studio** Hedgehog (2023.1.1) oder neuer
- **Android SDK** API 34 (min. API 23)
- **Temi SDK** 1.137.1 (wird automatisch via Maven geladen)
- **Temi-Roboter** mit installierter Developer-App

---

## 📂 Projektstruktur

```
app/
├── src/main/
│   ├── java/com/hoehmann/salesbot/
│   │   ├── MainActivity.kt              # Haupt-Activity mit UI-Logik
│   │   ├── robot/
│   │   │   └── TemiRobotManager.kt      # Temi SDK Steuerung (TTS, Navigation)
│   │   ├── data/
│   │   │   ├── ServiceItem.kt           # Data class für Leistungen
│   │   │   └── ServicesRepository.kt    # Alle 6 Leistungen
│   │   └── ui/
│   │       └── ServiceAdapter.kt        # RecyclerView Adapter
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml        # Haupt-Layout
│   │   │   └── item_service.xml         # Service-Kachel Layout
│   │   └── values/
│   │       ├── colors.xml
│   │       └── strings.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

---

## ⚙️ Standorte konfigurieren

Damit die Navigation funktioniert, müssen auf dem Temi-Roboter folgende **Locations** angelegt werden:

| App-Konstante   | Temi-Location Name |
|-----------------|--------------------|
| `ENTRANCE`      | `Eingang`          |
| `IT_SERVICES`   | `IT-Services`      |
| `TELEPHONES`    | `Telefonanlagen`   |
| `HARDWARE`      | `Hardware`         |
| `CHECKOUT`      | `Kasse`            |
| `HOME_BASE`     | `home base`        |

**Standorte anlegen:**
1. Temi-Roboter in die gewünschte Position bringen
2. In der Temi-App: **Einstellungen → Orte → Neuen Ort hinzufügen**
3. Namen exakt wie in der Tabelle oben eingeben

---

## 🚀 Deployment auf den Temi-Roboter

1. **App bauen:**
   ```bash
   ./gradlew assembleDebug
   ```

2. **APK auf den Temi laden:**
   ```bash
   adb connect <TEMI_IP>:5555
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

3. **Als Kiosk-App einstellen:**
   - Temi-Einstellungen öffnen
   - **Kiosk Mode** aktivieren
   - **Hoehmann IT Berater** als Kiosk-App auswählen

4. **Roboter neu starten** – Die App startet automatisch

---

## 📚 Ressourcen

- 🤖 [Temi SDK auf GitHub](https://github.com/robotemi/sdk)
- 📖 [Temi Developer Documentation](https://www.robotemi.com/sdk/)
- 🏢 [Hoehmann IT & Kommunikation](https://it-hoehmann.com)
