package com.smartmedicinedoctor.app.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smartmedicinedoctor.app.R
import com.smartmedicinedoctor.app.databinding.ActivityCameraBinding
import com.smartmedicinedoctor.app.viewmodel.CameraViewModel
import java.io.File
import java.io.FileOutputStream

class CameraActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCameraBinding
    private lateinit var viewModel: CameraViewModel
    
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            processImage()
        } else {
            Toast.makeText(this, "فشل في التقاط الصورة", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            processImageFromGallery(it)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[CameraViewModel::class.java]
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        binding.apply {
            // Take photo button
            btnTakePhoto.setOnClickListener {
                takePhoto()
            }
            
            // Select from gallery button
            btnSelectFromGallery.setOnClickListener {
                selectFromGallery()
            }
            
            // Back button
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.processingState.observe(this) { isProcessing ->
            binding.progressBar.visibility = if (isProcessing) android.view.View.VISIBLE else android.view.View.GONE
            binding.btnTakePhoto.isEnabled = !isProcessing
            binding.btnSelectFromGallery.isEnabled = !isProcessing
        }
        
        viewModel.processingResult.observe(this) { result ->
            when (result) {
                is CameraViewModel.ProcessingResult.Success -> {
                    Toast.makeText(this, R.string.image_processed, Toast.LENGTH_SHORT).show()
                    // Navigate to prescription detail activity
                    val intent = Intent(this, PrescriptionDetailActivity::class.java).apply {
                        putExtra("prescription_id", result.prescriptionId)
                    }
                    startActivity(intent)
                    finish()
                }
                is CameraViewModel.ProcessingResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    
    private fun takePhoto() {
        val photoFile = createImageFile()
        val photoUri = androidx.core.content.FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )
        takePictureLauncher.launch(photoUri)
    }
    
    private fun selectFromGallery() {
        pickImageLauncher.launch("image/*")
    }
    
    private fun createImageFile(): File {
        val timeStamp = java.text.SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault()).format(java.util.Date())
        val imageFileName = "PRESCRIPTION_$timeStamp"
        val storageDir = getExternalFilesDir(null)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }
    
    private fun processImage() {
        // Get the captured image file
        val imageFile = File(getExternalFilesDir(null), "PRESCRIPTION_${System.currentTimeMillis()}.jpg")
        if (imageFile.exists()) {
            viewModel.processImage(imageFile.absolutePath)
        }
    }
    
    private fun processImageFromGallery(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            
            // Save bitmap to file
            val imageFile = File(getExternalFilesDir(null), "PRESCRIPTION_GALLERY_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.close()
            
            viewModel.processImage(imageFile.absolutePath)
        } catch (e: Exception) {
            Toast.makeText(this, "فشل في معالجة الصورة", Toast.LENGTH_SHORT).show()
        }
    }
}