# Médecin Médicament Intelligent

Une application Android gratuite et open-source qui aide les patients à gérer leurs médicaments de manière intelligente et sûre en utilisant l'intelligence artificielle.

## 🌟 Fonctionnalités Principales

### 📸 Lecture des Ordonnances
- Capturer des images d'ordonnances médicales (imprimées ou manuscrites)
- Extraire le texte en utilisant ML Kit Text Recognition
- Support des langues arabe, française et anglaise

### 🔍 Extraction d'Informations sur les Médicaments
- Extraire les noms, dosages et horaires des médicaments
- Analyser le texte en utilisant des motifs Regex avancés
- Afficher les informations de manière organisée et claire

### 💊 Explications des Médicaments
- Explication simple de chaque médicament en trois langues
- Informations sur l'utilisation et les effets secondaires
- Avertissements importants pour les patients

### ⚠️ Détection des Interactions Médicamenteuses
- Vérifier les interactions entre les médicaments
- Avertissements immédiats pour les risques potentiels
- Conseils d'utilisation sûre

### ⏰ Système de Rappels
- Programmer les heures de prise des médicaments
- Notifications audio et visuelles
- Rappels quotidiens et hebdomadaires

### 🎙️ Rappels Vocaux
- Lire le nom du médicament et le dosage à voix haute
- Support de prononciation multilingue
- Notifications audio personnalisées

## 🛠️ Technologies Utilisées

### Base
- **Kotlin**: Langage de programmation principal
- **Android Jetpack**: Composants de développement modernes
- **Room Database**: Base de données locale
- **WorkManager**: Gestion des tâches en arrière-plan

### Intelligence Artificielle
- **ML Kit Text Recognition**: Extraction de texte à partir d'images
- **Motifs Regex**: Analyse de texte et extraction d'informations
- **IA basée sur des règles**: Explications et interactions des médicaments

### Interface Utilisateur
- **Material Design 3**: Design moderne et attrayant
- **ViewBinding**: Liaison sécurisée des interfaces
- **Navigation Component**: Navigation entre écrans

### Fonctionnalités Supplémentaires
- **Text-to-Speech**: Rappels vocaux
- **CameraX**: Capture d'images
- **FileProvider**: Partage sécurisé de fichiers
- **Splash Screen API**: Écran de démarrage moderne

## 📱 Exigences Système

- **Android**: 7.0 (API 24) ou plus récent
- **RAM**: Au moins 2GB
- **Stockage**: 100MB d'espace libre
- **Caméra**: Requise pour prendre des photos

## 🚀 Installation et Construction

### Prérequis
- Android Studio Arctic Fox ou plus récent
- JDK 11 ou plus récent
- Android SDK API 34

### Étapes de Construction

1. **Cloner le projet**
```bash
git clone https://github.com/yourusername/smart-medicine-doctor.git
cd smart-medicine-doctor
```

2. **Exécuter le script de configuration**
```bash
./setup.sh  # Sur Linux/macOS
# ou
setup.bat   # Sur Windows
```

3. **Mettre à jour le chemin du SDK Android**
   - Ouvrir le fichier `local.properties`
   - Mettre à jour `sdk.dir` avec le chemin de votre SDK Android :

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

4. **Construire l'application**
```bash
./gradlew assembleDebug
```

5. **Installer sur l'appareil**
```bash
./gradlew installDebug
```

## 📁 Structure du Projet

```
app/
├── src/main/
│   ├── java/com/smartmedicinedoctor/app/
│   │   ├── data/                    # Base de données et entités
│   │   │   ├── AppDatabase.kt
│   │   │   ├── Medicine.kt
│   │   │   ├── Prescription.kt
│   │   │   ├── MedicineReminder.kt
│   │   │   └── dao/                 # Interfaces d'accès aux données
│   │   ├── repository/              # Référentiel de données
│   │   ├── ui/                      # Interfaces utilisateur
│   │   │   ├── MainActivity.kt
│   │   │   ├── CameraActivity.kt
│   │   │   ├── PrescriptionDetailActivity.kt
│   │   │   ├── MedicineDetailActivity.kt
│   │   │   └── MedicineReminderActivity.kt
│   │   ├── viewmodel/               # Modèles de vue
│   │   ├── utils/                   # Utilitaires d'aide
│   │   │   ├── OCRProcessor.kt
│   │   │   ├── MedicineExtractor.kt
│   │   │   ├── AIExplainer.kt
│   │   │   └── TextToSpeechHelper.kt
│   │   ├── worker/                  # Tâches en arrière-plan
│   │   └── SmartMedicineDoctorApp.kt
│   ├── res/                         # Ressources
│   │   ├── layout/                  # Fichiers de mise en page
│   │   ├── values/                  # Valeurs et chaînes
│   │   ├── drawable/                # Graphiques
│   │   └── xml/                     # Fichiers XML
│   └── AndroidManifest.xml
└── build.gradle
```

## 🔧 Configuration et Permissions

### Permissions Requises
- `CAMERA`: Capturer des images d'ordonnances
- `READ_EXTERNAL_STORAGE`: Lire les images de la galerie
- `WRITE_EXTERNAL_STORAGE`: Sauvegarder les images traitées
- `RECORD_AUDIO`: Rappels vocaux
- `SCHEDULE_EXACT_ALARM`: Programmer les rappels
- `POST_NOTIFICATIONS`: Envoyer des notifications
- `VIBRATE`: Vibrations de rappel
- `WAKE_LOCK`: Réveiller l'appareil pour les rappels

## 🌐 Langues Supportées

- **Arabe**: Langue principale
- **Français**: Support complet
- **Anglais**: Support complet

## 🔒 Sécurité et Confidentialité

- **Opération entièrement locale**: Aucune donnée envoyée aux serveurs externes
- **Chiffrement des données**: Chiffrement de la base de données locale
- **Permissions limitées**: Demander seulement les permissions nécessaires
- **Transparence du code**: Code open source pour inspection

## 🤝 Contribution

Nous accueillons vos contributions ! Veuillez suivre ces étapes :

1. Fork le projet
2. Créer une branche de fonctionnalité (`git checkout -b feature/AmazingFeature`)
3. Commiter vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Pousser vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

### Standards de Code
- Utiliser Kotlin
- Suivre les standards Android
- Ajouter des commentaires en arabe
- Tester les nouvelles fonctionnalités

## 📄 Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 🆘 Support et Aide

- **Problèmes**: Ouvrir un nouveau Issue
- **Suggestions**: Utiliser Discussions
- **Questions**: Consulter Wiki ou poser une question

## 🔮 Plans Futurs

- [ ] Support pour plus de langues
- [ ] Améliorer la précision OCR
- [ ] Ajouter une base de données complète d'interactions médicamenteuses
- [ ] Support pour les ordonnances électroniques
- [ ] Synchronisation avec les appareils portables
- [ ] Interface web d'accompagnement
- [ ] Support d'impression directe

## 📊 Statistiques

- **Téléchargements**: [À ajouter]
- **Évaluation**: [À ajouter]
- **Contributeurs**: [À ajouter]

## 📚 Documentation dans d'Autres Langues

- **[العربية](README.md)** - الوثائق باللغة العربية
- **[English](README_EN.md)** - English documentation

---

**Note**: Cette application est conçue à des fins éducatives et d'assistance générale. Elle ne remplace pas la consultation médicale professionnelle.

**Développeurs**: Équipe Médecin Médicament Intelligent

**Version**: 1.0.0

**Dernière Mise à Jour**: Décembre 2024