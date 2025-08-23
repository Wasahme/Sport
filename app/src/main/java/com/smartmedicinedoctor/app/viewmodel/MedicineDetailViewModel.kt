package com.smartmedicinedoctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smartmedicinedoctor.app.data.AppDatabase
import com.smartmedicinedoctor.app.data.Medicine
import com.smartmedicinedoctor.app.repository.MedicineRepository
import kotlinx.coroutines.launch

class MedicineDetailViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = MedicineRepository(
        AppDatabase.getDatabase(application).prescriptionDao(),
        AppDatabase.getDatabase(application).medicineDao(),
        AppDatabase.getDatabase(application).medicineReminderDao()
    )
    
    private val _medicine = MutableLiveData<Medicine>()
    val medicine: LiveData<Medicine> = _medicine
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    fun loadMedicine(medicineId: Long) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                
                val medicine = repository.getMedicineById(medicineId)
                if (medicine != null) {
                    _medicine.value = medicine
                } else {
                    _error.value = "الدواء غير موجود"
                }
                
            } catch (e: Exception) {
                _error.value = "فشل في تحميل الدواء: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
    
    fun updateMedicine(medicine: Medicine) {
        viewModelScope.launch {
            try {
                repository.updateMedicine(medicine)
                _medicine.value = medicine
            } catch (e: Exception) {
                _error.value = "فشل في تحديث الدواء: ${e.message}"
            }
        }
    }
    
    fun deleteMedicine() {
        viewModelScope.launch {
            try {
                _medicine.value?.let { medicine ->
                    repository.deleteMedicineWithReminders(medicine)
                    // Navigate back or show success message
                }
            } catch (e: Exception) {
                _error.value = "فشل في حذف الدواء: ${e.message}"
            }
        }
    }
}