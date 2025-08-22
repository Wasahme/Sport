package com.fittracker.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittracker.app.data.local.dao.StepDataDao
import com.fittracker.app.data.local.dao.WaterEntryDao
import com.fittracker.app.data.local.dao.FoodEntryDao
import com.fittracker.app.domain.model.StepSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val stepDataDao: StepDataDao,
    private val waterEntryDao: WaterEntryDao,
    private val foodEntryDao: FoodEntryDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                val today = Date()
                val userId = "default_user" // سيتم تحديثه لاحقاً

                // تحميل بيانات الخطوات
                val totalSteps = stepDataDao.getTotalStepsByDate(userId, today) ?: 0
                val totalDistance = stepDataDao.getTotalDistanceByDate(userId, today) ?: 0f
                val totalCalories = stepDataDao.getTotalCaloriesByDate(userId, today) ?: 0f

                // تحميل بيانات الماء
                val totalWater = waterEntryDao.getTotalWaterByDate(userId, today) ?: 0f

                // تحميل بيانات التغذية
                val foodCalories = foodEntryDao.getTotalCaloriesByDate(userId, today) ?: 0f
                val protein = foodEntryDao.getTotalProteinByDate(userId, today) ?: 0f
                val carbs = foodEntryDao.getTotalCarbsByDate(userId, today) ?: 0f
                val fat = foodEntryDao.getTotalFatByDate(userId, today) ?: 0f

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalSteps = totalSteps,
                    totalDistance = totalDistance,
                    totalCalories = totalCalories,
                    waterIntake = totalWater.toInt(),
                    foodCalories = foodCalories.toInt(),
                    protein = protein.toInt(),
                    carbs = carbs.toInt(),
                    fat = fat.toInt(),
                    activeMinutes = (totalSteps / 100).toInt()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "خطأ في تحميل البيانات"
                )
            }
        }
    }

    fun refreshData() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        loadDashboardData()
    }

    fun updateWaterIntake(amount: Int) {
        viewModelScope.launch {
            try {
                val userId = "default_user"
                val today = Date()
                
                // إضافة مدخل ماء جديد
                val waterEntry = com.fittracker.app.domain.model.WaterEntry(
                    userId = userId,
                    amount = amount.toFloat(),
                    date = today,
                    time = today
                )
                
                waterEntryDao.insertWaterEntry(waterEntry)
                
                // تحديث الواجهة
                val newTotalWater = _uiState.value.waterIntake + amount
                _uiState.value = _uiState.value.copy(waterIntake = newTotalWater)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "خطأ في إضافة الماء: ${e.message}"
                )
            }
        }
    }

    fun updateFoodCalories(calories: Int) {
        viewModelScope.launch {
            try {
                val userId = "default_user"
                val today = Date()
                
                // إضافة مدخل طعام جديد (مؤقت)
                val foodEntry = com.fittracker.app.domain.model.FoodEntry(
                    userId = userId,
                    foodId = 0,
                    foodName = "طعام مؤقت",
                    mealType = com.fittracker.app.domain.model.MealType.SNACKS,
                    servingSize = 1f,
                    servingUnit = "حصة",
                    calories = calories.toFloat(),
                    protein = 0f,
                    carbs = 0f,
                    fat = 0f,
                    fiber = 0f,
                    date = today,
                    time = today
                )
                
                foodEntryDao.insertFoodEntry(foodEntry)
                
                // تحديث الواجهة
                val newTotalCalories = _uiState.value.foodCalories + calories
                _uiState.value = _uiState.value.copy(foodCalories = newTotalCalories)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "خطأ في إضافة السعرات: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class DashboardUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val totalSteps: Int = 0,
    val totalDistance: Float = 0f,
    val totalCalories: Float = 0f,
    val waterIntake: Int = 0,
    val foodCalories: Int = 0,
    val protein: Int = 0,
    val carbs: Int = 0,
    val fat: Int = 0,
    val activeMinutes: Int = 0
)