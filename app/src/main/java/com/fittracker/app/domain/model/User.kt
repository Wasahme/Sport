package com.fittracker.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val dateOfBirth: Date? = null,
    val gender: Gender = Gender.OTHER,
    val height: Float? = null, // بالسم
    val weight: Float? = null, // بالكجم
    val activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    val fitnessGoal: FitnessGoal = FitnessGoal.GENERAL_FITNESS,
    val dailyStepGoal: Int = 10000,
    val dailyCalorieGoal: Int = 2000,
    val dailyWaterGoal: Int = 2000, // بالمل
    val profileImageUrl: String? = null,
    val isPremium: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val lastLoginAt: Date? = null,
    val timezone: String = "Asia/Riyadh",
    val language: String = "ar",
    val units: UnitSystem = UnitSystem.METRIC
)

enum class Gender {
    MALE, FEMALE, OTHER
}

enum class ActivityLevel {
    SEDENTARY,      // قليل النشاط
    LIGHT,          // نشاط خفيف
    MODERATE,       // نشاط معتدل
    ACTIVE,         // نشط
    VERY_ACTIVE     // نشط جداً
}

enum class FitnessGoal {
    WEIGHT_LOSS,        // فقدان الوزن
    WEIGHT_GAIN,        // زيادة الوزن
    MUSCLE_GAIN,        // بناء العضلات
    GENERAL_FITNESS,    // لياقة عامة
    ENDURANCE,          // التحمل
    STRENGTH,           // القوة
    FLEXIBILITY         // المرونة
}

enum class UnitSystem {
    METRIC,     // متري (كجم، سم، كم)
    IMPERIAL    // إمبراطوري (رطل، بوصة، ميل)
}