package com.smartmedicinedoctor.app.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smartmedicinedoctor.app.data.AppDatabase
import com.smartmedicinedoctor.app.data.Medicine
import com.smartmedicinedoctor.app.data.Prescription
import com.smartmedicinedoctor.app.repository.MedicineRepository
import com.smartmedicinedoctor.app.utils.AIExplainer
import com.smartmedicinedoctor.app.utils.MedicineExtractor
import com.smartmedicinedoctor.app.utils.OCRProcessor
import kotlinx.coroutines.launch
import java.io.File

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = MedicineRepository(
        AppDatabase.getDatabase(application).prescriptionDao(),
        AppDatabase.getDatabase(application).medicineDao(),
        AppDatabase.getDatabase(application).medicineReminderDao()
    )
    
    private val ocrProcessor = OCRProcessor(application)
    private val medicineExtractor = MedicineExtractor()
    private val aiExplainer = AIExplainer(application)
    
    private val _processingState = MutableLiveData<Boolean>()
    val processingState: LiveData<Boolean> = _processingState
    
    private val _processingResult = MutableLiveData<ProcessingResult>()
    val processingResult: LiveData<ProcessingResult> = _processingResult
    
    fun processImage(imagePath: String) {
        viewModelScope.launch {
            try {
                _processingState.value = true
                
                // Load image
                val imageFile = File(imagePath)
                if (!imageFile.exists()) {
                    _processingResult.value = ProcessingResult.Error("الصورة غير موجودة")
                    return@launch
                }
                
                val bitmap = BitmapFactory.decodeFile(imagePath)
                if (bitmap == null) {
                    _processingResult.value = ProcessingResult.Error("فشل في تحميل الصورة")
                    return@launch
                }
                
                // Extract text using OCR
                val extractedText = ocrProcessor.extractTextFromImage(bitmap)
                if (extractedText.isBlank()) {
                    _processingResult.value = ProcessingResult.Error("لم يتم العثور على نص في الصورة")
                    return@launch
                }
                
                // Save prescription
                val prescription = Prescription(
                    imagePath = imagePath,
                    extractedText = extractedText
                )
                val prescriptionId = repository.insertPrescription(prescription)
                
                // Extract medicines
                val medicines = medicineExtractor.extractMedicines(extractedText, prescriptionId)
                
                if (medicines.isNotEmpty()) {
                    // Add AI explanations for each medicine
                    val medicinesWithExplanations = medicines.map { medicine ->
                        val explanation = aiExplainer.explainMedicine(
                            medicine.name,
                            medicine.dosage,
                            medicine.frequency
                        )
                        medicine.copy(aiExplanation = explanation)
                    }
                    
                    repository.insertMedicines(medicinesWithExplanations)
                }
                
                _processingResult.value = ProcessingResult.Success(prescriptionId)
                
            } catch (e: Exception) {
                _processingResult.value = ProcessingResult.Error("فشل في معالجة الصورة: ${e.message}")
            } finally {
                _processingState.value = false
            }
        }
    }
    
    sealed class ProcessingResult {
        data class Success(val prescriptionId: Long) : ProcessingResult()
        data class Error(val message: String) : ProcessingResult()
    }
    
    override fun onCleared() {
        super.onCleared()
        ocrProcessor.release()
        aiExplainer.release()
    }
}