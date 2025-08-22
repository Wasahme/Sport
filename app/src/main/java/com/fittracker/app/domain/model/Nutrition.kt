package com.fittracker.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val nameAr: String,
    val brand: String? = null,
    val barcode: String? = null,
    val category: FoodCategory,
    val servingSize: Float,
    val servingUnit: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    val fiber: Float,
    val sugar: Float,
    val sodium: Float,
    val imageUrl: String? = null,
    val isCustom: Boolean = false,
    val createdBy: String? = null,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class FoodCategory {
    BREAKFAST,      // فطور
    LUNCH,          // غداء
    DINNER,         // عشاء
    SNACKS,         // وجبات خفيفة
    BEVERAGES,      // مشروبات
    FRUITS,         // فواكه
    VEGETABLES,     // خضروات
    PROTEIN,        // بروتين
    GRAINS,         // حبوب
    DAIRY,          // ألبان
    NUTS,           // مكسرات
    SWEETS,         // حلويات
    FAST_FOOD,      // وجبات سريعة
    SUPPLEMENTS     // مكملات
}

@Entity(tableName = "food_entries")
data class FoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val foodId: Long,
    val foodName: String,
    val mealType: MealType,
    val servingSize: Float,
    val servingUnit: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    val fiber: Float,
    val date: Date,
    val time: Date,
    val notes: String? = null,
    val imageUrl: String? = null,
    val createdAt: Date = Date()
)

enum class MealType {
    BREAKFAST,      // فطور
    MORNING_SNACK,  // وجبة خفيفة صباحية
    LUNCH,          // غداء
    AFTERNOON_SNACK, // وجبة خفيفة بعد الظهر
    DINNER,         // عشاء
    EVENING_SNACK   // وجبة خفيفة مسائية
}

@Entity(tableName = "water_entries")
data class WaterEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val amount: Float, // بالمل
    val date: Date,
    val time: Date,
    val notes: String? = null,
    val createdAt: Date = Date()
)

data class NutritionSummary(
    val date: Date,
    val totalCalories: Float,
    val totalProtein: Float,
    val totalCarbs: Float,
    val totalFat: Float,
    val totalFiber: Float,
    val totalWater: Float,
    val calorieGoal: Float,
    val proteinGoal: Float,
    val carbsGoal: Float,
    val fatGoal: Float,
    val waterGoal: Float,
    val caloriePercentage: Float,
    val proteinPercentage: Float,
    val carbsPercentage: Float,
    val fatPercentage: Float,
    val waterPercentage: Float
)

data class NutritionGoal(
    val userId: String,
    val dailyCalories: Float = 2000f,
    val dailyProtein: Float = 150f, // بالجرام
    val dailyCarbs: Float = 250f,   // بالجرام
    val dailyFat: Float = 65f,      // بالجرام
    val dailyFiber: Float = 25f,    // بالجرام
    val dailyWater: Float = 2000f,  // بالمل
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val name: String,
    val nameAr: String,
    val description: String? = null,
    val descriptionAr: String? = null,
    val category: FoodCategory,
    val difficulty: RecipeDifficulty = RecipeDifficulty.EASY,
    val prepTime: Int, // بالدقائق
    val cookTime: Int, // بالدقائق
    val servings: Int,
    val ingredients: List<RecipeIngredient>,
    val instructions: List<String>,
    val instructionsAr: List<String>,
    val nutritionPerServing: NutritionInfo,
    val imageUrl: String? = null,
    val isPublic: Boolean = false,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class RecipeDifficulty {
    EASY,       // سهل
    MEDIUM,     // متوسط
    HARD        // صعب
}

data class RecipeIngredient(
    val foodId: Long,
    val foodName: String,
    val amount: Float,
    val unit: String
)

data class NutritionInfo(
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    val fiber: Float
)