# طبيب دواء ذكي (Smart Medicine Doctor)

تطبيق أندرويد مجاني ومفتوح المصدر لمساعدة المرضى في إدارة الأدوية والوصفات الطبية باستخدام الذكاء الاصطناعي.

## 🌟 الميزات الرئيسية

### 📸 قراءة الوصفات الطبية
- التقاط صور للوصفات الطبية (مطبوعة أو مكتوبة بخط اليد)
- استخراج النصوص باستخدام ML Kit Text Recognition
- دعم اللغات العربية والفرنسية والإنجليزية

### 🔍 استخراج معلومات الأدوية
- استخراج أسماء الأدوية والجرعات والمواعيد
- تحليل النصوص باستخدام أنماط Regex متقدمة
- عرض المعلومات بشكل منظم وواضح

### 💊 شرح الأدوية
- شرح مبسط لكل دواء باللغات الثلاث
- معلومات عن الاستخدام والآثار الجانبية
- تحذيرات مهمة للمريض

### ⚠️ كشف التفاعلات الدوائية
- فحص التفاعلات بين الأدوية
- تحذيرات فورية للمخاطر المحتملة
- نصائح للاستخدام الآمن

### ⏰ نظام التذكيرات
- جدولة مواعيد أخذ الأدوية
- إشعارات صوتية ومرئية
- تذكيرات يومية وأسبوعية

### 🎙️ التذكير الصوتي
- قراءة اسم الدواء والجرعة بصوت واضح
- دعم اللغات المتعددة في النطق
- إشعارات صوتية مخصصة

## 🛠️ التقنيات المستخدمة

### الأساسية
- **Kotlin**: لغة البرمجة الرئيسية
- **Android Jetpack**: مكونات التطوير الحديثة
- **Room Database**: قاعدة بيانات محلية
- **WorkManager**: إدارة المهام الخلفية

### الذكاء الاصطناعي
- **ML Kit Text Recognition**: استخراج النصوص من الصور
- **Regex Patterns**: تحليل النصوص واستخراج المعلومات
- **Rule-based AI**: شرح الأدوية والتفاعلات

### واجهة المستخدم
- **Material Design 3**: تصميم حديث وجذاب
- **ViewBinding**: ربط آمن للواجهات
- **Navigation Component**: التنقل بين الشاشات

### الميزات الإضافية
- **Text-to-Speech**: التذكير الصوتي
- **CameraX**: التقاط الصور
- **FileProvider**: مشاركة الملفات الآمنة
- **Splash Screen API**: شاشة البداية الحديثة

## 📱 متطلبات النظام

- **Android**: 7.0 (API 24) أو أحدث
- **RAM**: 2GB على الأقل
- **Storage**: 100MB مساحة خالية
- **Camera**: مطلوب للتقاط الصور

## 🚀 التثبيت والبناء

### المتطلبات المسبقة
- Android Studio Arctic Fox أو أحدث
- JDK 11 أو أحدث
- Android SDK API 34

### تثبيت Android SDK

#### الطريقة الأولى: تثبيت Android Studio
1. قم بتحميل Android Studio من [الموقع الرسمي](https://developer.android.com/studio)
2. قم بتثبيت Android Studio
3. عند فتح Android Studio لأول مرة، سيتم تثبيت Android SDK تلقائياً

#### الطريقة الثانية: تثبيت Android SDK فقط (للمطورين المتقدمين)
1. قم بتحميل Android SDK من [الموقع الرسمي](https://developer.android.com/studio#command-tools)
2. استخرج الملف في مجلد مناسب
3. أضف مسار SDK إلى متغير البيئة ANDROID_HOME

### إعداد المشروع

1. **استنساخ المشروع**
```bash
git clone https://github.com/yourusername/smart-medicine-doctor.git
cd smart-medicine-doctor
```

2. **تحديث مسار Android SDK**
   - افتح ملف `local.properties`
   - قم بتحديث `sdk.dir` بمسار Android SDK الخاص بك:
   
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

3. **فتح المشروع في Android Studio**
   - افتح Android Studio
   - اختر "Open an existing project"
   - اختر مجلد المشروع

4. **مزامنة Gradle**
```bash
./gradlew build
```

5. **بناء التطبيق**
```bash
./gradlew assembleDebug
```

6. **تثبيت على الجهاز**
```bash
./gradlew installDebug
```

### حل المشاكل الشائعة

#### مشكلة: "SDK location not found"
**الحل:** تأكد من تحديث مسار Android SDK في ملف `local.properties`

#### مشكلة: "Gradle wrapper not found"
**الحل:** تأكد من وجود ملف `gradle-wrapper.jar` في مجلد `gradle/wrapper/`

#### مشكلة: "Plugin not found"
**الحل:** تأكد من وجود اتصال بالإنترنت لتحميل المكتبات

## 📁 هيكل المشروع

```
app/
├── src/main/
│   ├── java/com/smartmedicinedoctor/app/
│   │   ├── data/                    # قاعدة البيانات والكيانات
│   │   │   ├── AppDatabase.kt
│   │   │   ├── Medicine.kt
│   │   │   ├── Prescription.kt
│   │   │   ├── MedicineReminder.kt
│   │   │   └── dao/                 # واجهات الوصول للبيانات
│   │   ├── repository/              # مستودع البيانات
│   │   ├── ui/                      # واجهات المستخدم
│   │   │   ├── MainActivity.kt
│   │   │   ├── CameraActivity.kt
│   │   │   ├── PrescriptionDetailActivity.kt
│   │   │   ├── MedicineDetailActivity.kt
│   │   │   └── MedicineReminderActivity.kt
│   │   ├── viewmodel/               # نماذج العرض
│   │   ├── utils/                   # الأدوات المساعدة
│   │   │   ├── OCRProcessor.kt
│   │   │   ├── MedicineExtractor.kt
│   │   │   ├── AIExplainer.kt
│   │   │   └── TextToSpeechHelper.kt
│   │   ├── worker/                  # مهام خلفية
│   │   └── SmartMedicineDoctorApp.kt
│   ├── res/                         # الموارد
│   │   ├── layout/                  # تخطيطات الواجهات
│   │   ├── values/                  # القيم والسلاسل النصية
│   │   ├── drawable/                # الرسومات
│   │   └── xml/                     # ملفات XML
│   └── AndroidManifest.xml
└── build.gradle
```

## 🔧 الإعدادات والصلاحيات

### الصلاحيات المطلوبة
- `CAMERA`: التقاط صور الوصفات
- `READ_EXTERNAL_STORAGE`: قراءة الصور من المعرض
- `WRITE_EXTERNAL_STORAGE`: حفظ الصور المعالجة
- `RECORD_AUDIO`: التذكير الصوتي
- `SCHEDULE_EXACT_ALARM`: جدولة التذكيرات
- `POST_NOTIFICATIONS`: إرسال الإشعارات
- `VIBRATE`: اهتزاز التذكيرات
- `WAKE_LOCK`: إيقاظ الجهاز للتذكيرات

## 🌐 اللغات المدعومة

- **العربية**: اللغة الرئيسية
- **الفرنسية**: دعم كامل
- **الإنجليزية**: دعم كامل

## 🔒 الأمان والخصوصية

- **عمل محلي بالكامل**: لا يتم إرسال أي بيانات إلى خوادم خارجية
- **تشفير البيانات**: تشفير قاعدة البيانات المحلية
- **صلاحيات محدودة**: طلب الصلاحيات الضرورية فقط
- **شفافية الكود**: كود مفتوح المصدر قابل للفحص

## 🤝 المساهمة

نرحب بمساهماتكم! يرجى اتباع الخطوات التالية:

1. Fork المشروع
2. إنشاء فرع للميزة الجديدة (`git checkout -b feature/AmazingFeature`)
3. Commit التغييرات (`git commit -m 'Add some AmazingFeature'`)
4. Push إلى الفرع (`git push origin feature/AmazingFeature`)
5. فتح Pull Request

### معايير الكود
- استخدام Kotlin
- اتباع معايير Android
- إضافة تعليقات باللغة العربية
- اختبار الوظائف الجديدة

## 📄 الترخيص

هذا المشروع مرخص تحت رخصة MIT - انظر ملف [LICENSE](LICENSE) للتفاصيل.

## 🆘 الدعم والمساعدة

- **المشاكل**: افتح Issue جديد
- **الاقتراحات**: استخدم Discussions
- **الأسئلة**: راجع Wiki أو اطرح سؤالاً

## 🔮 الخطط المستقبلية

- [ ] دعم المزيد من اللغات
- [ ] تحسين دقة OCR
- [ ] إضافة قاعدة بيانات تفاعلات دوائية شاملة
- [ ] دعم الوصفات الإلكترونية
- [ ] مزامنة مع الأجهزة القابلة للارتداء
- [ ] واجهة ويب مصاحبة
- [ ] دعم الطباعة المباشرة

## 📊 الإحصائيات

- **التحميلات**: [سيتم إضافة الإحصائيات]
- **التقييم**: [سيتم إضافة التقييم]
- **المساهمون**: [سيتم إضافة المساهمين]

## 📚 الوثائق باللغات الأخرى

- **[English](README_EN.md)** - English documentation
- **[Français](README_FR.md)** - Documentation en français

---

**ملاحظة**: هذا التطبيق مخصص للأغراض التعليمية والمساعدة العامة. لا يحل محل استشارة الطبيب المختص.

**المطورون**: فريق طبيب دواء ذكي

**الإصدار**: 1.0.0

**تاريخ آخر تحديث**: ديسمبر 2024