package com.fittracker.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val nameAr: String,
    val description: String? = null,
    val descriptionAr: String? = null,
    val category: ExerciseCategory,
    val difficulty: ExerciseDifficulty = ExerciseDifficulty.BEGINNER,
    val equipment: List<Equipment> = emptyList(),
    val muscleGroups: List<MuscleGroup> = emptyList(),
    val instructions: List<String> = emptyList(),
    val instructionsAr: List<String> = emptyList(),
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val caloriesPerMinute: Float? = null,
    val isCustom: Boolean = false,
    val createdBy: String? = null,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class ExerciseCategory {
    CARDIO,         // كارديو
    STRENGTH,       // قوة
    FLEXIBILITY,    // مرونة
    CROSSFIT,       // كروسفت
    YOGA,           // يوغا
    PILATES,        // بيلاتس
    SPORTS,         // رياضات
    FUNCTIONAL      // تمارين وظيفية
}

enum class ExerciseDifficulty {
    BEGINNER,       // مبتدئ
    INTERMEDIATE,   // متوسط
    ADVANCED,       // متقدم
    EXPERT          // خبير
}

enum class Equipment {
    NONE,           // بدون معدات
    DUMBBELLS,      // دمبل
    BARBELL,        // بار
    RESISTANCE_BANDS, // شرائط مقاومة
    KETTLEBELL,     // كيتل بيل
    MACHINE,        // آلة
    BODYWEIGHT,     // وزن الجسم
    CARDIO_MACHINE, // آلة كارديو
    YOGA_MAT,       // بساط يوغا
    FOAM_ROLLER     // بكرة رغوية
}

enum class MuscleGroup {
    CHEST,          // الصدر
    BACK,           // الظهر
    SHOULDERS,      // الكتفين
    BICEPS,         // العضلة ذات الرأسين
    TRICEPS,        // العضلة ثلاثية الرؤوس
    FOREARMS,       // الساعدين
    ABS,            // عضلات البطن
    OBLIQUES,       // العضلات المائلة
    LOWER_BACK,     // أسفل الظهر
    GLUTES,         // الأرداف
    QUADS,          // عضلات الفخذ الأمامية
    HAMSTRINGS,     // عضلات الفخذ الخلفية
    CALVES,         // عضلات الساق
    FULL_BODY,      // الجسم كاملاً
    CARDIO          // كارديو
}

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val name: String,
    val description: String? = null,
    val exercises: List<WorkoutExercise>,
    val duration: Int, // بالدقائق
    val difficulty: ExerciseDifficulty,
    val category: ExerciseCategory,
    val isTemplate: Boolean = false,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class WorkoutExercise(
    val exerciseId: Long,
    val order: Int,
    val sets: Int,
    val reps: Int? = null,
    val duration: Int? = null, // بالثواني
    val weight: Float? = null, // بالكجم
    val restTime: Int = 60, // بالثواني
    val notes: String? = null
)

@Entity(tableName = "workout_sessions")
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val workoutId: Long? = null,
    val workoutName: String,
    val startTime: Date,
    val endTime: Date? = null,
    val duration: Int? = null, // بالدقائق
    val exercises: List<WorkoutSessionExercise>,
    val totalCalories: Float = 0f,
    val isCompleted: Boolean = false,
    val notes: String? = null,
    val createdAt: Date = Date()
)

data class WorkoutSessionExercise(
    val exerciseId: Long,
    val exerciseName: String,
    val order: Int,
    val sets: List<ExerciseSet>,
    val totalCalories: Float = 0f
)

data class ExerciseSet(
    val setNumber: Int,
    val reps: Int? = null,
    val duration: Int? = null, // بالثواني
    val weight: Float? = null, // بالكجم
    val isCompleted: Boolean = false,
    val notes: String? = null
)