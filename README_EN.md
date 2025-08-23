# Smart Medicine Doctor

A free, open-source Android application that helps patients manage their medications intelligently and safely using artificial intelligence.

## 🌟 Key Features

### 📸 Prescription Reading
- Capture images of medical prescriptions (printed or handwritten)
- Extract text using ML Kit Text Recognition
- Support for Arabic, French, and English languages

### 🔍 Medicine Information Extraction
- Extract medicine names, dosages, and schedules
- Analyze text using advanced Regex patterns
- Display information in an organized and clear manner

### 💊 Medicine Explanations
- Simple explanation of each medicine in three languages
- Information about usage and side effects
- Important warnings for patients

### ⚠️ Drug Interaction Detection
- Check interactions between medications
- Immediate warnings for potential risks
- Safe usage tips

### ⏰ Reminder System
- Schedule medication intake times
- Audio and visual notifications
- Daily and weekly reminders

### 🎙️ Voice Reminders
- Read medicine name and dosage in clear voice
- Multi-language pronunciation support
- Customized audio notifications

## 🛠️ Technologies Used

### Core
- **Kotlin**: Primary programming language
- **Android Jetpack**: Modern development components
- **Room Database**: Local database
- **WorkManager**: Background task management

### Artificial Intelligence
- **ML Kit Text Recognition**: Text extraction from images
- **Regex Patterns**: Text analysis and information extraction
- **Rule-based AI**: Medicine explanations and interactions

### User Interface
- **Material Design 3**: Modern and attractive design
- **ViewBinding**: Safe interface binding
- **Navigation Component**: Screen navigation

### Additional Features
- **Text-to-Speech**: Voice reminders
- **CameraX**: Image capture
- **FileProvider**: Secure file sharing
- **Splash Screen API**: Modern splash screen

## 📱 System Requirements

- **Android**: 7.0 (API 24) or later
- **RAM**: At least 2GB
- **Storage**: 100MB free space
- **Camera**: Required for taking photos

## 🚀 Installation and Building

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK API 34

### Building Steps

1. **Clone the project**
```bash
git clone https://github.com/yourusername/smart-medicine-doctor.git
cd smart-medicine-doctor
```

2. **Run setup script**
```bash
./setup.sh  # On Linux/macOS
# or
setup.bat   # On Windows
```

3. **Update Android SDK path**
   - Open `local.properties` file
   - Update `sdk.dir` with your Android SDK path:

   **Windows:**
   ```
   sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
   ```

   **macOS:**
   ```
   sdk.dir=/Users/YourUsername/Library/Android/sdk
   ```

   **Linux:**
   ```
   sdk.dir=/home/YourUsername/Android/Sdk
   ```

4. **Build the application**
```bash
./gradlew assembleDebug
```

5. **Install on device**
```bash
./gradlew installDebug
```

## 📁 Project Structure

```
app/
├── src/main/
│   ├── java/com/smartmedicinedoctor/app/
│   │   ├── data/                    # Database and entities
│   │   │   ├── AppDatabase.kt
│   │   │   ├── Medicine.kt
│   │   │   ├── Prescription.kt
│   │   │   ├── MedicineReminder.kt
│   │   │   └── dao/                 # Data access interfaces
│   │   ├── repository/              # Data repository
│   │   ├── ui/                      # User interfaces
│   │   │   ├── MainActivity.kt
│   │   │   ├── CameraActivity.kt
│   │   │   ├── PrescriptionDetailActivity.kt
│   │   │   ├── MedicineDetailActivity.kt
│   │   │   └── MedicineReminderActivity.kt
│   │   ├── viewmodel/               # View models
│   │   ├── utils/                   # Helper utilities
│   │   │   ├── OCRProcessor.kt
│   │   │   ├── MedicineExtractor.kt
│   │   │   ├── AIExplainer.kt
│   │   │   └── TextToSpeechHelper.kt
│   │   ├── worker/                  # Background tasks
│   │   └── SmartMedicineDoctorApp.kt
│   ├── res/                         # Resources
│   │   ├── layout/                  # Layout files
│   │   ├── values/                  # Values and strings
│   │   ├── drawable/                # Graphics
│   │   └── xml/                     # XML files
│   └── AndroidManifest.xml
└── build.gradle
```

## 🔧 Configuration and Permissions

### Required Permissions
- `CAMERA`: Capture prescription images
- `READ_EXTERNAL_STORAGE`: Read images from gallery
- `WRITE_EXTERNAL_STORAGE`: Save processed images
- `RECORD_AUDIO`: Voice reminders
- `SCHEDULE_EXACT_ALARM`: Schedule reminders
- `POST_NOTIFICATIONS`: Send notifications
- `VIBRATE`: Reminder vibrations
- `WAKE_LOCK`: Wake device for reminders

## 🌐 Supported Languages

- **Arabic**: Primary language
- **French**: Full support
- **English**: Full support

## 🔒 Security and Privacy

- **Fully local operation**: No data sent to external servers
- **Data encryption**: Local database encryption
- **Limited permissions**: Request only necessary permissions
- **Code transparency**: Open source code for inspection

## 🤝 Contributing

We welcome your contributions! Please follow these steps:

1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Standards
- Use Kotlin
- Follow Android standards
- Add comments in Arabic
- Test new features

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support and Help

- **Issues**: Open a new Issue
- **Suggestions**: Use Discussions
- **Questions**: Review Wiki or ask a question

## 🔮 Future Plans

- [ ] Support for more languages
- [ ] Improve OCR accuracy
- [ ] Add comprehensive drug interaction database
- [ ] Support for electronic prescriptions
- [ ] Sync with wearable devices
- [ ] Companion web interface
- [ ] Direct printing support

## 📊 Statistics

- **Downloads**: [To be added]
- **Rating**: [To be added]
- **Contributors**: [To be added]

## 📚 Documentation in Other Languages

- **[العربية](README.md)** - الوثائق باللغة العربية
- **[Français](README_FR.md)** - Documentation en français

---

**Note**: This application is designed for educational purposes and general assistance. It does not replace professional medical consultation.

**Developers**: Smart Medicine Doctor Team

**Version**: 1.0.0

**Last Updated**: December 2024