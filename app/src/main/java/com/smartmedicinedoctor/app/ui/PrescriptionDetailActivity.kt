package com.smartmedicinedoctor.app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartmedicinedoctor.app.R
import com.smartmedicinedoctor.app.databinding.ActivityPrescriptionDetailBinding
import com.smartmedicinedoctor.app.viewmodel.PrescriptionDetailViewModel

class PrescriptionDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPrescriptionDetailBinding
    private lateinit var viewModel: PrescriptionDetailViewModel
    private lateinit var medicineAdapter: MedicineAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrescriptionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val prescriptionId = intent.getLongExtra("prescription_id", -1)
        if (prescriptionId == -1L) {
            Toast.makeText(this, "خطأ في تحميل الوصفة", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        viewModel = ViewModelProvider(this)[PrescriptionDetailViewModel::class.java]
        viewModel.loadPrescription(prescriptionId)
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        binding.apply {
            // Back button
            btnBack.setOnClickListener {
                finish()
            }
            
            // Set up RecyclerView
            medicineAdapter = MedicineAdapter { medicine ->
                // Handle medicine click - show medicine details
                showMedicineDetails(medicine)
            }
            
            recyclerViewMedicines.apply {
                layoutManager = LinearLayoutManager(this@PrescriptionDetailActivity)
                adapter = medicineAdapter
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.prescription.observe(this) { prescription ->
            prescription?.let {
                binding.apply {
                    tvExtractedText.text = it.extractedText
                    tvDoctorName.text = it.doctorName.ifEmpty { "غير محدد" }
                    tvPatientName.text = it.patientName.ifEmpty { "غير محدد" }
                    tvDate.text = it.date.ifEmpty { "غير محدد" }
                }
            }
        }
        
        viewModel.medicines.observe(this) { medicines ->
            medicineAdapter.submitList(medicines)
            binding.tvMedicineCount.text = "${medicines.size} دواء"
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
    
    private fun showMedicineDetails(medicine: com.smartmedicinedoctor.app.data.Medicine) {
        // Show medicine details dialog or navigate to medicine detail activity
        val intent = Intent(this, MedicineDetailActivity::class.java).apply {
            putExtra("medicine_id", medicine.id)
        }
        startActivity(intent)
    }
}