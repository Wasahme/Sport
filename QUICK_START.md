# البدء السريع - طبيب دواء ذكي

## ⚡ البدء في 5 دقائق

### 1. تثبيت المتطلبات الأساسية

#### Java (مطلوب)
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-11-jdk

# macOS
brew install openjdk@11

# Windows
# قم بتحميل Java من https://www.oracle.com/java/technologies/downloads/
```

#### Android Studio (موصى به)
1. قم بتحميل Android Studio من [هنا](https://developer.android.com/studio)
2. ثبت Android Studio
3. افتح Android Studio واتبع خطوات الإعداد

### 2. استنساخ المشروع
```bash
git clone https://github.com/yourusername/smart-medicine-doctor.git
cd smart-medicine-doctor
```

### 3. تشغيل الإعداد التلقائي

#### Linux/macOS:
```bash
./setup.sh
```

#### Windows:
```cmd
setup.bat
```

### 4. تحديث مسار Android SDK

افتح ملف `local.properties` وحدد مسار Android SDK:

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

### 5. بناء التطبيق
```bash
./gradlew assembleDebug
```

### 6. تثبيت على الجهاز
```bash
./gradlew installDebug
```

## 🎯 استخدام التطبيق

### الخطوات الأساسية:

1. **فتح التطبيق** - ستظهر شاشة البداية
2. **إضافة وصفة** - اضغط "إضافة وصفة جديدة"
3. **التقاط صورة** - التقط صورة للوصفة الطبية
4. **مراجعة النتائج** - راجع الأدوية المستخرجة
5. **إعداد تذكيرات** - اضبط مواعيد تناول الأدوية

### الميزات الرئيسية:

- 📸 **قراءة الوصفات** - استخراج النصوص من الصور
- 💊 **تحليل الأدوية** - استخراج الجرعات والمواعيد
- ⏰ **التذكيرات** - إشعارات صوتية ومرئية
- 🌐 **اللغات المتعددة** - العربية، الفرنسية، الإنجليزية

## 🔧 حل المشاكل السريع

### مشكلة: "SDK location not found"
```bash
# تأكد من تحديث local.properties
echo "sdk.dir=/path/to/your/android/sdk" > local.properties
```

### مشكلة: "Gradle wrapper not found"
```bash
# إعادة تحميل gradle wrapper
curl -L -o gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/v8.4.0/gradle/wrapper/gradle-wrapper.jar
chmod +x gradlew
```

### مشكلة: "Plugin not found"
```bash
# مسح cache وإعادة البناء
./gradlew clean
./gradlew build
```

## 📱 متطلبات الجهاز

- **Android**: 7.0 (API 24) أو أحدث
- **RAM**: 2GB على الأقل
- **Storage**: 100MB مساحة خالية
- **Camera**: مطلوب للتقاط الصور
- **Microphone**: للتذكيرات الصوتية

## 🚀 الأوامر المفيدة

```bash
# بناء التطبيق
./gradlew assembleDebug

# تثبيت على الجهاز
./gradlew installDebug

# مسح cache
./gradlew clean

# تشغيل الاختبارات
./gradlew test

# بناء نسخة الإنتاج
./gradlew assembleRelease
```

## 📞 الحصول على المساعدة

- 📖 **الدليل الكامل**: راجع `INSTALL.md`
- 📚 **الوثائق**: راجع `README.md`
- 🐛 **المشاكل**: افتح Issue على GitHub
- 💬 **الأسئلة**: استخدم Discussions

---

**ملاحظة**: هذا دليل سريع للبدء. للحصول على معلومات مفصلة، راجع `INSTALL.md` و `README.md`.