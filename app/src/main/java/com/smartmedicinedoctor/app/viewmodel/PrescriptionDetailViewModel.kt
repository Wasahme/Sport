package com.smartmedicinedoctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smartmedicinedoctor.app.data.AppDatabase
import com.smartmedicinedoctor.app.data.Medicine
import com.smartmedicinedoctor.app.data.Prescription
import com.smartmedicinedoctor.app.repository.MedicineRepository
import kotlinx.coroutines.launch

class PrescriptionDetailViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = MedicineRepository(
        AppDatabase.getDatabase(application).prescriptionDao(),
        AppDatabase.getDatabase(application).medicineDao(),
        AppDatabase.getDatabase(application).medicineReminderDao()
    )
    
    private val _prescription = MutableLiveData<Prescription>()
    val prescription: LiveData<Prescription> = _prescription
    
    private val _medicines = MutableLiveData<List<Medicine>>()
    val medicines: LiveData<List<Medicine>> = _medicines
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    fun loadPrescription(prescriptionId: Long) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                
                // Load prescription
                val prescription = repository.getPrescriptionById(prescriptionId)
                if (prescription != null) {
                    _prescription.value = prescription
                    
                    // Load medicines for this prescription
                    repository.getMedicinesByPrescriptionId(prescriptionId).collect { medicines ->
                        _medicines.value = medicines
                    }
                } else {
                    _error.value = "الوصفة غير موجودة"
                }
                
            } catch (e: Exception) {
                _error.value = "فشل في تحميل الوصفة: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
    
    fun deletePrescription() {
        viewModelScope.launch {
            try {
                _prescription.value?.let { prescription ->
                    repository.deletePrescriptionWithMedicines(prescription)
                    // Navigate back or show success message
                }
            } catch (e: Exception) {
                _error.value = "فشل في حذف الوصفة: ${e.message}"
            }
        }
    }
    
    fun deleteMedicine(medicine: Medicine) {
        viewModelScope.launch {
            try {
                repository.deleteMedicineWithReminders(medicine)
                // Refresh medicines list
                _prescription.value?.let { prescription ->
                    repository.getMedicinesByPrescriptionId(prescription.id).collect { medicines ->
                        _medicines.value = medicines
                    }
                }
            } catch (e: Exception) {
                _error.value = "فشل في حذف الدواء: ${e.message}"
            }
        }
    }
}