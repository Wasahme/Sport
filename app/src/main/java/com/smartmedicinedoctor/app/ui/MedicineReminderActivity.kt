package com.smartmedicinedoctor.app.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smartmedicinedoctor.app.R
import com.smartmedicinedoctor.app.databinding.ActivityMedicineReminderBinding
import java.text.SimpleDateFormat
import java.util.*

class MedicineReminderActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMedicineReminderBinding
    private lateinit var viewModel: MedicineReminderViewModel
    
    private var selectedTime = Calendar.getInstance()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val medicineId = intent.getLongExtra("medicine_id", -1)
        if (medicineId == -1L) {
            Toast.makeText(this, "خطأ في تحميل الدواء", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        viewModel = ViewModelProvider(this)[MedicineReminderViewModel::class.java]
        viewModel.loadMedicine(medicineId)
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        binding.apply {
            // Back button
            btnBack.setOnClickListener {
                finish()
            }
            
            // Time picker
            btnSelectTime.setOnClickListener {
                showTimePicker()
            }
            
            // Save reminder button
            btnSaveReminder.setOnClickListener {
                saveReminder()
            }
            
            // Day selection checkboxes
            checkboxMonday.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedDays()
            }
            checkboxTuesday.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedDays()
            }
            checkboxWednesday.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedDays()
            }
            checkboxThursday.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedDays()
            }
            checkboxFriday.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedDays()
            }
            checkboxSaturday.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedDays()
            }
            checkboxSunday.setOnCheckedChangeListener { _, isChecked ->
                updateSelectedDays()
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.medicine.observe(this) { medicine ->
            medicine?.let {
                binding.tvMedicineName.text = it.name
                binding.tvDosage.text = it.dosage
            }
        }
        
        viewModel.saving.observe(this) { isSaving ->
            binding.progressBar.visibility = if (isSaving) android.view.View.VISIBLE else android.view.View.GONE
            binding.btnSaveReminder.isEnabled = !isSaving
        }
        
        viewModel.saveResult.observe(this) { result ->
            when (result) {
                is MedicineReminderViewModel.SaveResult.Success -> {
                    Toast.makeText(this, R.string.reminder_saved, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is MedicineReminderViewModel.SaveResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    
    private fun showTimePicker() {
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                binding.tvSelectedTime.text = timeFormat.format(selectedTime.time)
            },
            selectedTime.get(Calendar.HOUR_OF_DAY),
            selectedTime.get(Calendar.MINUTE),
            true
        ).show()
    }
    
    private fun updateSelectedDays() {
        val selectedDays = mutableListOf<Int>()
        
        if (binding.checkboxMonday.isChecked) selectedDays.add(1)
        if (binding.checkboxTuesday.isChecked) selectedDays.add(2)
        if (binding.checkboxWednesday.isChecked) selectedDays.add(3)
        if (binding.checkboxThursday.isChecked) selectedDays.add(4)
        if (binding.checkboxFriday.isChecked) selectedDays.add(5)
        if (binding.checkboxSaturday.isChecked) selectedDays.add(6)
        if (binding.checkboxSunday.isChecked) selectedDays.add(7)
        
        viewModel.selectedDays.value = selectedDays
    }
    
    private fun saveReminder() {
        val time = timeFormat.format(selectedTime.time)
        val soundEnabled = binding.switchSound.isChecked
        val vibrationEnabled = binding.switchVibration.isChecked
        
        viewModel.saveReminder(time, soundEnabled, vibrationEnabled)
    }
}