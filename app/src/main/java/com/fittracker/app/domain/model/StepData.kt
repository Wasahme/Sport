package com.fittracker.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "step_data")
data class StepData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val date: Date,
    val steps: Int,
    val distance: Float, // بالكم
    val calories: Float,
    val activeMinutes: Int,
    val sedentaryMinutes: Int,
    val startTime: Date,
    val endTime: Date,
    val source: StepSource = StepSource.SENSOR,
    val isSynced: Boolean = false,
    val createdAt: Date = Date()
)

enum class StepSource {
    SENSOR,         // مستشعر الجهاز
    HEALTH_CONNECT, // Health Connect
    GOOGLE_FIT,     // Google Fit
    MANUAL          // إدخال يدوي
}

data class StepSummary(
    val date: Date,
    val totalSteps: Int,
    val totalDistance: Float,
    val totalCalories: Float,
    val activeMinutes: Int,
    val goalAchieved: Boolean,
    val goalPercentage: Float
)

data class StepGoal(
    val userId: String,
    val dailyGoal: Int = 10000,
    val weeklyGoal: Int = 70000,
    val monthlyGoal: Int = 300000,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)