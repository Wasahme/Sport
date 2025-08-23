# دليل التثبيت - طبيب دواء ذكي

## 📋 المتطلبات المسبقة

### 1. تثبيت Java
- **Java 11 أو أحدث** مطلوب لتشغيل Gradle
- يمكنك تحميله من [موقع Oracle](https://www.oracle.com/java/technologies/downloads/) أو استخدام OpenJDK

**للتحقق من تثبيت Java:**
```bash
java -version
```

### 2. تثبيت Android SDK

#### الطريقة الأولى: تثبيت Android Studio (موصى بها)
1. قم بتحميل Android Studio من [الموقع الرسمي](https://developer.android.com/studio)
2. قم بتثبيت Android Studio
3. عند فتح Android Studio لأول مرة:
   - اختر "Standard" setup
   - سيتم تثبيت Android SDK تلقائياً
   - تأكد من تثبيت Android SDK API 34

#### الطريقة الثانية: تثبيت Android SDK فقط
1. قم بتحميل Android SDK Command Line Tools من [هنا](https://developer.android.com/studio#command-tools)
2. استخرج الملف في مجلد مناسب (مثل `C:\Android\Sdk` على Windows)
3. أضف مسار SDK إلى متغير البيئة ANDROID_HOME

## 🚀 خطوات التثبيت

### الخطوة 1: استنساخ المشروع
```bash
git clone https://github.com/yourusername/smart-medicine-doctor.git
cd smart-medicine-doctor
```

### الخطوة 2: تشغيل script الإعداد

#### على Linux/macOS:
```bash
./setup.sh
```

#### على Windows:
```cmd
setup.bat
```

### الخطوة 3: تحديث مسار Android SDK

افتح ملف `local.properties` وقم بتحديث `sdk.dir` بمسار Android SDK الخاص بك:

#### Windows:
```
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
```

#### macOS:
```
sdk.dir=/Users/YourUsername/Library/Android/sdk
```

#### Linux:
```
sdk.dir=/home/YourUsername/Android/Sdk
```

### الخطوة 4: بناء التطبيق

#### مزامنة Gradle:
```bash
./gradlew build
```

#### بناء APK:
```bash
./gradlew assembleDebug
```

#### تثبيت على الجهاز:
```bash
./gradlew installDebug
```

## 🔧 حل المشاكل الشائعة

### مشكلة: "SDK location not found"
**الأعراض:** خطأ عند تشغيل `./gradlew build`
```
SDK location not found. Define a valid SDK location with an ANDROID_HOME environment variable or by setting the sdk.dir path in your project's local.properties file.
```

**الحل:**
1. تأكد من تثبيت Android SDK
2. حدد مسار SDK في ملف `local.properties`
3. أو اضبط متغير البيئة ANDROID_HOME

### مشكلة: "Gradle wrapper not found"
**الأعراض:** خطأ عند تشغيل `./gradlew`
```
bash: ./gradlew: No such file or directory
```

**الحل:**
1. تأكد من وجود ملف `gradle-wrapper.jar` في مجلد `gradle/wrapper/`
2. شغل script الإعداد: `./setup.sh` أو `setup.bat`

### مشكلة: "Plugin not found"
**الأعراض:** خطأ عند بناء المشروع
```
Plugin [id: 'kotlin-kapt', version: '1.9.10', apply: false] was not found
```

**الحل:**
1. تأكد من وجود اتصال بالإنترنت
2. امسح cache: `./gradlew clean`
3. أعد البناء: `./gradlew build`

### مشكلة: "Java version not supported"
**الأعراض:** خطأ يتعلق بإصدار Java
```
Java 11 or higher is required
```

**الحل:**
1. تأكد من تثبيت Java 11 أو أحدث
2. تحقق من إصدار Java: `java -version`
3. إذا كان لديك إصدارات متعددة، تأكد من استخدام الإصدار الصحيح

## 📱 تثبيت التطبيق على الجهاز

### متطلبات الجهاز:
- Android 7.0 (API 24) أو أحدث
- مساحة تخزين: 100MB على الأقل
- كاميرا للتقاط الصور
- ميكروفون للتذكيرات الصوتية

### خطوات التثبيت:

#### الطريقة الأولى: عبر USB
1. فعّل "Developer options" على جهازك
2. فعّل "USB debugging"
3. اربط الجهاز بالكمبيوتر
4. شغل: `./gradlew installDebug`

#### الطريقة الثانية: عبر APK
1. شغل: `./gradlew assembleRelease`
2. ابحث عن ملف APK في: `app/build/outputs/apk/release/`
3. انسخ الملف إلى جهازك
4. فعّل "Install from unknown sources"
5. ثبت التطبيق

## 🎯 التحقق من التثبيت

بعد تثبيت التطبيق، تأكد من:

1. **فتح التطبيق** - يجب أن تظهر شاشة البداية
2. **طلب الصلاحيات** - الكاميرا، التخزين، الإشعارات
3. **التقاط صورة** - اختبار الكاميرا
4. **إعداد تذكير** - اختبار نظام التذكيرات

## 📞 الدعم

إذا واجهت أي مشاكل:

1. راجع هذا الدليل
2. تحقق من ملف README.md
3. افتح Issue جديد على GitHub
4. راجع logs البناء للحصول على تفاصيل الخطأ

## 🔄 التحديثات

لتحديث التطبيق:

```bash
git pull origin main
./gradlew clean
./gradlew assembleDebug
./gradlew installDebug
```

---

**ملاحظة:** هذا التطبيق مخصص للأغراض التعليمية والمساعدة العامة. لا يحل محل استشارة الطبيب المختص.