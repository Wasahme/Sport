package com.smartmedicinedoctor.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.smartmedicinedoctor.app.data.AppDatabase
import com.smartmedicinedoctor.app.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val repository = MedicineRepository(
        database.prescriptionDao(),
        database.medicineDao(),
        database.medicineReminderDao()
    )
    
    private val _prescriptionCount = MutableStateFlow(0)
    val prescriptionCount: StateFlow<Int> = _prescriptionCount
    
    private val _activeReminderCount = MutableStateFlow(0)
    val activeReminderCount: StateFlow<Int> = _activeReminderCount
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            // Load prescription count
            repository.getAllPrescriptions().collect { prescriptions ->
                _prescriptionCount.value = prescriptions.size
            }
        }
        
        viewModelScope.launch {
            // Load active reminder count
            repository.getActiveReminders().collect { reminders ->
                _activeReminderCount.value = reminders.size
            }
        }
    }
    
    fun refreshData() {
        loadData()
    }
}