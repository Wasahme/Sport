package com.smartmedicinedoctor.app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smartmedicinedoctor.app.R
import com.smartmedicinedoctor.app.databinding.ActivityMedicineDetailBinding
import com.smartmedicinedoctor.app.viewmodel.MedicineDetailViewModel

class MedicineDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMedicineDetailBinding
    private lateinit var viewModel: MedicineDetailViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val medicineId = intent.getLongExtra("medicine_id", -1)
        if (medicineId == -1L) {
            Toast.makeText(this, "خطأ في تحميل الدواء", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        viewModel = ViewModelProvider(this)[MedicineDetailViewModel::class.java]
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
            
            // Set reminder button
            btnSetReminder.setOnClickListener {
                // Navigate to reminder activity
                val intent = Intent(this@MedicineDetailActivity, MedicineReminderActivity::class.java).apply {
                    putExtra("medicine_id", viewModel.medicine.value?.id ?: -1)
                }
                startActivity(intent)
            }
            
            // Share button
            btnShare.setOnClickListener {
                shareMedicineInfo()
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.medicine.observe(this) { medicine ->
            medicine?.let {
                binding.apply {
                    tvMedicineName.text = it.name
                    tvDosage.text = it.dosage
                    tvFrequency.text = it.frequency
                    tvDuration.text = it.duration
                    tvInstructions.text = it.instructions
                    tvAiExplanation.text = it.aiExplanation
                    tvSideEffects.text = it.sideEffects.ifEmpty { "غير محدد" }
                    tvPrecautions.text = it.precautions.ifEmpty { "غير محدد" }
                }
            }
        }
        
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }
        
        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun shareMedicineInfo() {
        viewModel.medicine.value?.let { medicine ->
            val shareText = """
                اسم الدواء: ${medicine.name}
                الجرعة: ${medicine.dosage}
                التكرار: ${medicine.frequency}
                المدة: ${medicine.duration}
                التعليمات: ${medicine.instructions}
                
                ${medicine.aiExplanation}
            """.trimIndent()
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(intent, "مشاركة معلومات الدواء"))
        }
    }
}