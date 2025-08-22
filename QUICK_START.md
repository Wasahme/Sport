# البدء السريع - تطبيق FitTracker

## 🚀 البدء في 5 دقائق

### الطريقة الأولى: باستخدام Make (الأسرع)

```bash
# 1. استنساخ المشروع
git clone https://github.com/yourusername/fittracker.git
cd fittracker

# 2. إعداد المشروع
make setup

# 3. بناء APK
make build

# 4. تثبيت على الجهاز (اختياري)
make install
```

### الطريقة الثانية: باستخدام Docker (بدون تثبيت Android Studio)

```bash
# 1. استنساخ المشروع
git clone https://github.com/yourusername/fittracker.git
cd fittracker

# 2. بناء APK باستخدام Docker
docker-compose --profile build up

# 3. العثور على APK
ls -la app/build/outputs/apk/debug/
```

### الطريقة الثالثة: باستخدام Android Studio

```bash
# 1. استنساخ المشروع
git clone https://github.com/yourusername/fittracker.git

# 2. فتح في Android Studio
# File > Open > اختر مجلد المشروع

# 3. انتظر تحميل Gradle

# 4. اضغط Run (▶️)
```

## 📱 تثبيت APK

### على الهاتف
1. فعّل "التثبيت من مصادر غير معروفة" في إعدادات الهاتف
2. انسخ APK إلى الهاتف
3. اضغط على APK للتثبيت

### باستخدام ADB
```bash
# ربط الهاتف بالكمبيوتر
adb devices

# تثبيت APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🎯 ما ستحصل عليه

بعد البناء الناجح، ستحصل على:

- ✅ **تطبيق FitTracker كامل** مع جميع الميزات
- ✅ **واجهة عربية** مع دعم RTL
- ✅ **5 شاشات رئيسية** مكتملة
- ✅ **قاعدة بيانات محلية** مع جميع الجداول
- ✅ **خدمات في الخلفية** لعداد الخطوات

## 🔧 استكشاف الأخطاء السريع

### خطأ: "SDK location not found"
```bash
# حل سريع باستخدام Docker
docker-compose --profile build up
```

### خطأ: "Permission denied"
```bash
# إصلاح صلاحيات الملفات
chmod +x gradlew
make setup
```

### خطأ: "Gradle wrapper not found"
```bash
# إعادة تحميل Gradle wrapper
wget -O gradle/wrapper/gradle-wrapper.jar https://github.com/gradle/gradle/raw/master/gradle/wrapper/gradle-wrapper.jar
```

## 📊 حجم APK المتوقع

- **Debug APK**: ~15-25 MB
- **Release APK**: ~10-20 MB

## ⚡ نصائح للسرعة

### تسريع البناء
```bash
# استخدام Docker مع cache
docker-compose --profile build up

# استخدام Make مع parallel build
make -j4 build
```

### تسريع التطوير
```bash
# سير العمل السريع للتطوير
make dev

# إعادة البناء السريع
make clean build
```

## 🎉 النتيجة

بعد اتباع هذه الخطوات، ستحصل على:

1. **APK جاهز للتثبيت** على أي هاتف أندرويد
2. **تطبيق كامل** مع جميع الميزات الأساسية
3. **واجهة عربية** احترافية
4. **قاعدة بيانات** جاهزة للاستخدام

## 📞 الحصول على المساعدة

إذا واجهت أي مشاكل:

1. اقرأ [BUILD_INSTRUCTIONS.md](BUILD_INSTRUCTIONS.md)
2. اقرأ [ANDROID_SDK_SETUP.md](ANDROID_SDK_SETUP.md)
3. افتح [Issue](https://github.com/yourusername/fittracker/issues)

---

**🎯 هدفنا**: جعل بناء تطبيق FitTracker أسهل وأسرع ما يمكن!