# تعليمات بناء تطبيق FitTracker

## نظرة عامة

هذا الملف يوضح جميع الطرق المتاحة لبناء تطبيق FitTracker وتحويله إلى APK جاهز للتثبيت.

## الطريقة الأولى: Android Studio (الأسهل)

### المتطلبات
- Android Studio (أحدث إصدار)
- Android SDK
- JDK 17 أو أحدث

### الخطوات
1. **فتح المشروع**
   ```bash
   # استنساخ المشروع
   git clone https://github.com/yourusername/fittracker.git
   cd fittracker
   
   # فتح في Android Studio
   # File > Open > اختر مجلد المشروع
   ```

2. **تثبيت التبعيات**
   - انتظر حتى يكتمل تحميل Gradle
   - تأكد من تثبيت جميع التبعيات

3. **بناء APK**
   - **للتطوير**: Build > Build Bundle(s) / APK(s) > Build APK(s)
   - **للإنتاج**: Build > Generate Signed Bundle / APK

4. **العثور على APK**
   - APK التطوير: `app/build/outputs/apk/debug/app-debug.apk`
   - APK الإنتاج: `app/build/outputs/apk/release/app-release.apk`

## الطريقة الثانية: Command Line (للمطورين)

### المتطلبات
- Android SDK
- JDK 17 أو أحدث
- Gradle (مدمج في المشروع)

### الخطوات
1. **إعداد Android SDK**
   ```bash
   # تعيين متغير البيئة
   export ANDROID_HOME=/path/to/your/android/sdk
   
   # أو إنشاء ملف local.properties
   echo "sdk.dir=/path/to/your/android/sdk" > local.properties
   ```

2. **بناء APK**
   ```bash
   # بناء APK للتطوير
   ./gradlew assembleDebug
   
   # بناء APK للإنتاج
   ./gradlew assembleRelease
   
   # تنظيف البناء
   ./gradlew clean
   ```

3. **العثور على APK**
   - APK التطوير: `app/build/outputs/apk/debug/app-debug.apk`
   - APK الإنتاج: `app/build/outputs/apk/release/app-release.apk`

## الطريقة الثالثة: Docker (بدون تثبيت Android Studio)

### المتطلبات
- Docker
- Docker Compose (اختياري)

### باستخدام Docker Compose
```bash
# بناء APK للتطوير
docker-compose --profile build up

# بناء APK للإنتاج
docker-compose --profile release up

# تنظيف الحاويات
docker-compose down
```

### باستخدام Docker مباشرة
```bash
# بناء صورة Docker
docker build -t fittracker-builder .

# بناء APK للتطوير
docker run -v $(pwd):/workspace fittracker-builder ./gradlew assembleDebug

# بناء APK للإنتاج
docker run -v $(pwd):/workspace fittracker-builder ./gradlew assembleRelease

# نسخ APK من الحاوية
docker cp $(docker ps -q):/workspace/app/build/outputs/apk/debug/app-debug.apk ./app-debug.apk
```

## الطريقة الرابعة: GitHub Actions (تلقائي)

### إعداد GitHub Actions
1. ارفع المشروع إلى GitHub
2. اذهب إلى Settings > Secrets and variables > Actions
3. أضف `ANDROID_SDK_ROOT` كـ secret
4. اذهب إلى Actions > New workflow
5. اختر "Android" template

### ملف Workflow المطلوب
```yaml
name: Android CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        
    - name: Build with Gradle
      run: ./gradlew assembleDebug
      
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
```

## الطريقة الخامسة: Jenkins CI/CD

### إعداد Jenkins
1. ثبت Jenkins على الخادم
2. ثبت Android SDK Plugin
3. أضف Android SDK إلى Jenkins
4. أنشئ Pipeline job

### ملف Jenkinsfile
```groovy
pipeline {
    agent any
    
    environment {
        ANDROID_HOME = '/opt/android-sdk'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh './gradlew assembleDebug'
            }
        }
        
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', fingerprint: true
            }
        }
    }
}
```

## استكشاف الأخطاء الشائعة

### خطأ: "SDK location not found"
**الحل**: اتبع تعليمات [إعداد Android SDK](ANDROID_SDK_SETUP.md)

### خطأ: "Gradle wrapper not found"
**الحل**: تأكد من وجود ملف `gradle-wrapper.jar` في `gradle/wrapper/`

### خطأ: "Build tools not found"
**الحل**: ثبت Android SDK Build-Tools من Android Studio

### خطأ: "License not accepted"
**الحل**: شغل `sdkmanager --licenses` وقبل جميع التراخيص

### خطأ: "Memory insufficient"
**الحل**: زد ذاكرة Gradle في `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=512m
```

## تحسين الأداء

### إعدادات Gradle
```properties
# في gradle.properties
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configuration-cache=true
org.gradle.daemon=true
```

### إعدادات Android
```properties
# في gradle.properties
android.enableR8.fullMode=true
android.enableJetifier=false
```

## التحقق من البناء

### فحص APK
```bash
# فحص حجم APK
ls -lh app/build/outputs/apk/debug/app-debug.apk

# فحص محتويات APK
unzip -l app/build/outputs/apk/debug/app-debug.apk

# فحص التوقيع
jarsigner -verify -verbose -certs app/build/outputs/apk/debug/app-debug.apk
```

### اختبار APK
```bash
# تثبيت APK على جهاز متصل
adb install app/build/outputs/apk/debug/app-debug.apk

# تشغيل التطبيق
adb shell am start -n com.fittracker.app/.presentation.ui.MainActivity
```

## النتائج المتوقعة

بعد البناء الناجح، ستجد:

### ملفات APK
- `app/build/outputs/apk/debug/app-debug.apk` (للتطوير)
- `app/build/outputs/apk/release/app-release.apk` (للإنتاج)

### حجم APK المتوقع
- Debug APK: ~15-25 MB
- Release APK: ~10-20 MB (مضغوط)

### وقت البناء المتوقع
- أول بناء: 5-10 دقائق
- البناءات اللاحقة: 1-3 دقائق

## روابط مفيدة

- [Android Build System](https://developer.android.com/studio/build)
- [Gradle Documentation](https://gradle.org/docs/)
- [Docker Documentation](https://docs.docker.com/)
- [GitHub Actions](https://docs.github.com/en/actions)
- [Jenkins Documentation](https://www.jenkins.io/doc/)