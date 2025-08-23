package com.smartmedicinedoctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smartmedicinedoctor.app.data.AppDatabase
import com.smartmedicinedoctor.app.data.Medicine
import com.smartmedicinedoctor.app.data.MedicineReminder
import com.smartmedicinedoctor.app.repository.MedicineRepository
import com.smartmedicinedoctor.app.utils.ReminderScheduler
import kotlinx.coroutines.launch

class MedicineReminderViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = MedicineRepository(
        AppDatabase.getDatabase(application).prescriptionDao(),
        AppDatabase.getDatabase(application).medicineDao(),
        AppDatabase.getDatabase(application).medicineReminderDao()
    )
    
    private val reminderScheduler = ReminderScheduler(application)
    
    private val _medicine = MutableLiveData<Medicine>()
    val medicine: LiveData<Medicine> = _medicine
    
    private val _selectedDays = MutableLiveData<List<Int>>()
    val selectedDays: LiveData<List<Int>> = _selectedDays
    
    private val _saving = MutableLiveData<Boolean>()
    val saving: LiveData<Boolean> = _saving
    
    private val _saveResult = MutableLiveData<SaveResult>()
    val saveResult: LiveData<SaveResult> = _saveResult
    
    fun loadMedicine(medicineId: Long) {
        viewModelScope.launch {
            try {
                val medicine = repository.getMedicineById(medicineId)
                if (medicine != null) {
                    _medicine.value = medicine
                } else {
                    _saveResult.value = SaveResult.Error("الدواء غير موجود")
                }
            } catch (e: Exception) {
                _saveResult.value = SaveResult.Error("فشل في تحميل الدواء: ${e.message}")
            }
        }
    }
    
    fun saveReminder(time: String, soundEnabled: Boolean, vibrationEnabled: Boolean) {
        viewModelScope.launch {
            try {
                _saving.value = true
                
                val medicine = _medicine.value
                val selectedDays = _selectedDays.value
                
                if (medicine == null) {
                    _saveResult.value = SaveResult.Error("الدواء غير محدد")
                    return@launch
                }
                
                if (selectedDays.isNullOrEmpty()) {
                    _saveResult.value = SaveResult.Error("يرجى اختيار يوم واحد على الأقل")
                    return@launch
                }
                
                val reminder = MedicineReminder(
                    medicineId = medicine.id,
                    medicineName = medicine.name,
                    dosage = medicine.dosage,
                    time = time,
                    days = selectedDays.joinToString(","),
                    soundEnabled = soundEnabled,
                    vibrationEnabled = vibrationEnabled
                )
                
                val reminderId = repository.insertReminder(reminder)
                
                // Schedule the reminder
                reminderScheduler.scheduleReminder(reminder.copy(id = reminderId))
                
                _saveResult.value = SaveResult.Success
                
            } catch (e: Exception) {
                _saveResult.value = SaveResult.Error("فشل في حفظ التذكير: ${e.message}")
            } finally {
                _saving.value = false
            }
        }
    }
    
    sealed class SaveResult {
        object Success : SaveResult()
        data class Error(val message: String) : SaveResult()
    }
}