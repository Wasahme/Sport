# إعداد Android SDK لتطبيق FitTracker

## المشكلة
تطبيق FitTracker يحتاج إلى Android SDK لبناء APK. إذا واجهت خطأ "SDK location not found"، اتبع هذه التعليمات.

## الحلول

### الحل الأول: تثبيت Android Studio (مُوصى)

#### 1. تحميل Android Studio
- اذهب إلى [https://developer.android.com/studio](https://developer.android.com/studio)
- حمل أحدث إصدار من Android Studio
- ثبت Android Studio على جهازك

#### 2. تثبيت Android SDK
1. افتح Android Studio
2. اذهب إلى **File > Settings** (Windows/Linux) أو **Android Studio > Preferences** (macOS)
3. اذهب إلى **Appearance & Behavior > System Settings > Android SDK**
4. تأكد من تثبيت:
   - Android SDK Platform 34 (أو أحدث)
   - Android SDK Build-Tools
   - Android SDK Platform-Tools
   - Android SDK Tools

#### 3. تحديد مسار SDK
1. في نفس نافذة Android SDK، انسخ مسار SDK
2. افتح ملف `local.properties` في مشروع FitTracker
3. استبدل `sdk.dir=/path/to/your/android/sdk` بالمسار الصحيح

### الحل الثاني: تثبيت Android SDK فقط

#### Windows
```bash
# تحميل Command Line Tools
# من https://developer.android.com/studio#command-tools

# إضافة إلى PATH
set ANDROID_HOME=C:\Users\YourUsername\AppData\Local\Android\Sdk
set PATH=%PATH%;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools
```

#### macOS
```bash
# تحميل Command Line Tools
# من https://developer.android.com/studio#command-tools

# إضافة إلى PATH
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

#### Linux
```bash
# تحميل Command Line Tools
# من https://developer.android.com/studio#command-tools

# إضافة إلى PATH
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

### الحل الثالث: استخدام Docker (للمطورين المتقدمين)

```dockerfile
FROM openjdk:17-jdk

# تثبيت Android SDK
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# تحميل Android Command Line Tools
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip
RUN unzip commandlinetools-linux-8512546_latest.zip -d /opt/android-sdk

# إعداد متغيرات البيئة
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# تثبيت Android SDK
RUN yes | sdkmanager --licenses
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

## التحقق من التثبيت

بعد تثبيت Android SDK، تحقق من التثبيت:

```bash
# التحقق من متغير ANDROID_HOME
echo $ANDROID_HOME

# التحقق من وجود adb
adb version

# التحقق من وجود sdkmanager
sdkmanager --list
```

## بناء التطبيق

بعد إعداد Android SDK، يمكنك بناء التطبيق:

```bash
# بناء APK للتطوير
./gradlew assembleDebug

# بناء APK للإنتاج
./gradlew assembleRelease
```

## المسارات المعتادة لـ Android SDK

### Windows
- `C:\Users\YourUsername\AppData\Local\Android\Sdk`
- `C:\Program Files\Android\Android Studio\Sdk`

### macOS
- `/Users/YourUsername/Library/Android/sdk`
- `/Applications/Android Studio.app/Contents/sdk`

### Linux
- `/home/YourUsername/Android/Sdk`
- `/opt/android-sdk`

## استكشاف الأخطاء

### خطأ: "SDK location not found"
- تأكد من وجود ملف `local.properties` في مجلد المشروع
- تأكد من صحة مسار SDK في `local.properties`
- تأكد من تثبيت Android SDK

### خطأ: "SDK tools not found"
- تأكد من تثبيت Android SDK Build-Tools
- تأكد من إضافة SDK إلى PATH

### خطأ: "License not accepted"
- شغل `sdkmanager --licenses` وقبل جميع التراخيص

## روابط مفيدة

- [Android Studio Download](https://developer.android.com/studio)
- [Android SDK Documentation](https://developer.android.com/studio/command-line)
- [Gradle Documentation](https://gradle.org/docs/)
- [Android Build System](https://developer.android.com/studio/build)