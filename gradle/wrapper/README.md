# Gradle Wrapper

هذا المجلد يحتوي على ملفات Gradle Wrapper المطلوبة لبناء المشروع.

## الملفات المطلوبة

- `gradle-wrapper.jar` - ملف JAR يحتوي على Gradle Wrapper
- `gradle-wrapper.properties` - إعدادات Gradle Wrapper

## كيفية الحصول على gradle-wrapper.jar

إذا لم يكن ملف `gradle-wrapper.jar` موجود، يمكنك الحصول عليه بإحدى الطرق التالية:

### الطريقة الأولى: من مشروع Android جديد
1. أنشئ مشروع Android جديد في Android Studio
2. انسخ ملف `gradle/wrapper/gradle-wrapper.jar` من المشروع الجديد

### الطريقة الثانية: من Gradle
```bash
gradle wrapper
```

### الطريقة الثالثة: تحميل مباشر
يمكنك تحميل الملف من:
https://github.com/gradle/gradle/raw/master/gradle/wrapper/gradle-wrapper.jar

## ملاحظات

- تأكد من أن إصدار Gradle في `gradle-wrapper.properties` متوافق مع إصدار Android Gradle Plugin
- لا تقم بحذف هذا المجلد أو محتوياته
- هذه الملفات ضرورية لبناء المشروع بدون تثبيت Gradle محلياً