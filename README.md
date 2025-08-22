# FitTracker - تطبيق متتبع اللياقة البدنية

## نظرة عامة

FitTracker هو تطبيق أندرويد شامل للياقة البدنية والصحة، مطور باستخدام **Kotlin** و **Jetpack Compose**. يوفر التطبيق مجموعة متكاملة من الميزات لتتبع النشاط البدني والتمارين والتغذية.

## الميزات الرئيسية

### 📱 عداد الخطوات المتقدم
- تتبع مستمر للخطوات باستخدام مستشعر الجهاز
- حساب دقيق للمسافة والسعرات المحروقة
- أهداف ذكية تتكيف مع نشاط المستخدم
- إحصائيات تفاعلية ورسوم بيانية

### 💪 نظام التمارين الذكي
- مكتبة شاملة للتمارين مع فئات متعددة
- خطط تمارين مخصصة حسب المستوى والهدف
- مؤقت تمارين مع إشعارات
- تتبع التقدم والإنجازات

### 🍎 متتبع التغذية المتكامل
- قاعدة بيانات محلية للأطعمة العربية والعالمية
- تتبع السعرات والماكروز
- مذكرة طعام يومية
- متتبع الماء مع تذكيرات

### 📊 لوحة التحكم التفاعلية
- واجهة عصرية باستخدام Material Design 3
- رسوم بيانية متحركة
- إحصائيات مفصلة
- نظام نقاط وشارات

## التقنيات المستخدمة

### اللغة والإطار
- **Kotlin** - لغة البرمجة الأساسية
- **Jetpack Compose** - واجهة المستخدم الحديثة
- **Coroutines & Flow** - البرمجة غير المتزامنة
- **Hilt** - حقن التبعيات
- **Navigation Compose** - التنقل بين الشاشات

### قواعد البيانات والتخزين
- **Room Database** - قاعدة البيانات المحلية
- **DataStore** - حفظ الإعدادات
- **SharedPreferences** - البيانات البسيطة

### واجهة المستخدم
- **Material Design 3** - نظام التصميم الحديث
- **Compose Animation** - الحركات والانتقالات
- **Dark/Light Theme** - دعم الوضع الليلي
- **RTL Support** - دعم كامل للعربية

### الاستشعار والأجهزة
- **Sensors API** - مستشعر الخطوات والحركة
- **CameraX** - الكاميرا للباركود
- **Health Connect** - تكامل مع تطبيقات الصحة

### الشبكات والخدمات
- **Retrofit** - التواصل مع APIs
- **Firebase** - خدمات سحابية
- **ML Kit** - التعرف على النصوص والصور

## معمارية التطبيق

### Clean Architecture
```
📱 Presentation Layer (UI)
├── Compose Screens
├── ViewModels
└── UI States

🔄 Domain Layer (Business Logic)
├── Use Cases
├── Models
└── Repository Interfaces

💾 Data Layer (Data Sources)
├── Room Database
├── Remote API
├── DataStore
└── Repository Implementation
```

### MVVM Pattern
- **Model** - قواعد البيانات والبيانات
- **View** - واجهة المستخدم (Compose)
- **ViewModel** - منطق العمل والحالة

## متطلبات النظام

### للتطوير
- Android Studio Arctic Fox+
- Kotlin 1.9+
- Gradle 8.0+
- JDK 17+
- Android SDK 34+

### للاستخدام
- Android 7.0+ (API 24+)
- 2GB RAM (مُوصى 4GB)
- 100MB مساحة تخزين
- مستشعرات: Accelerometer, Step Counter

## التثبيت والتشغيل

### 1. استنساخ المشروع
```bash
git clone https://github.com/yourusername/fittracker.git
cd fittracker
```

### 2. فتح المشروع في Android Studio
- افتح Android Studio
- اختر "Open an existing project"
- اختر مجلد المشروع

### 3. تثبيت التبعيات
- انتظر حتى يكتمل تحميل Gradle
- تأكد من تثبيت جميع التبعيات

### 4. تشغيل التطبيق
- اربط جهاز أندرويد أو استخدم المحاكي
- اضغط على زر Run (▶️)

## هيكل المشروع

```
app/
├── src/main/
│   ├── java/com/fittracker/app/
│   │   ├── data/           # طبقة البيانات
│   │   │   ├── local/      # قاعدة البيانات المحلية
│   │   │   ├── remote/     # APIs والخدمات السحابية
│   │   │   └── repository/ # تنفيذ المستودعات
│   │   ├── domain/         # طبقة المنطق التجاري
│   │   │   ├── model/      # نماذج البيانات
│   │   │   ├── repository/ # واجهات المستودعات
│   │   │   └── usecase/    # حالات الاستخدام
│   │   ├── presentation/   # طبقة العرض
│   │   │   ├── ui/         # واجهات المستخدم
│   │   │   └── viewmodel/  # نماذج العرض
│   │   ├── di/             # حقن التبعيات
│   │   └── utils/          # أدوات مساعدة
│   └── res/                # الموارد
│       ├── drawable/       # الصور والأيقونات
│       ├── values/         # النصوص والألوان
│       └── xml/            # ملفات XML
```

## الميزات المتقدمة

### الذكاء الاصطناعي
- **TensorFlow Lite** - نماذج ذكية محلية
- **ML Kit** - التعرف على النصوص والصور
- **Custom Models** - نماذج مخصصة للتنبؤات

### التكامل مع النظام
- **Health Connect** - مزامنة البيانات الصحية
- **Google Fit** - تكامل مع بيانات اللياقة
- **Calendar Integration** - ربط مع التقويم

### الإشعارات الذكية
- **Push Notifications** - إشعارات فورية
- **Local Notifications** - تذكيرات محلية
- **Rich Notifications** - إشعارات تفاعلية

## المساهمة

نرحب بمساهماتكم! يرجى اتباع الخطوات التالية:

1. Fork المشروع
2. إنشاء فرع جديد للميزة (`git checkout -b feature/AmazingFeature`)
3. Commit التغييرات (`git commit -m 'Add some AmazingFeature'`)
4. Push إلى الفرع (`git push origin feature/AmazingFeature`)
5. فتح Pull Request

## الترخيص

هذا المشروع مرخص تحت رخصة MIT - انظر ملف [LICENSE](LICENSE) للتفاصيل.

## الدعم

إذا واجهت أي مشاكل أو لديك أسئلة:

- افتح [Issue](https://github.com/yourusername/fittracker/issues)
- راسلنا على: support@fittracker.com

## الإصدارات

### v1.0.0 (الحالي)
- ✅ عداد الخطوات الأساسي
- ✅ واجهة المستخدم الأساسية
- ✅ قاعدة البيانات المحلية
- ✅ نظام التنقل
- 🔄 متتبع التغذية (قيد التطوير)
- 🔄 نظام التمارين (قيد التطوير)

### المخطط للإصدارات القادمة
- v1.1.0 - متتبع التغذية الكامل
- v1.2.0 - نظام التمارين المتقدم
- v1.3.0 - التكامل مع Health Connect
- v2.0.0 - Kotlin Multiplatform لـ iOS

---

**FitTracker** - تطبيقك الشامل للياقة البدنية والصحة 🏃‍♂️💪