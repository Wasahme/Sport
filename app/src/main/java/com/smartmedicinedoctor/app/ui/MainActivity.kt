package com.smartmedicinedoctor.app.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.smartmedicinedoctor.app.R
import com.smartmedicinedoctor.app.databinding.ActivityMainBinding
import com.smartmedicinedoctor.app.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            // Permissions granted, proceed with camera functionality
            openCamera()
        } else {
            Toast.makeText(this, R.string.camera_permission_required, Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        binding.apply {
            // Add prescription button
            btnAddPrescription.setOnClickListener {
                checkPermissionsAndOpenCamera()
            }
            
            // My prescriptions button
            btnMyPrescriptions.setOnClickListener {
                // Navigate to prescriptions list
                // For now, just show a toast
                Toast.makeText(this@MainActivity, R.string.my_prescriptions, Toast.LENGTH_SHORT).show()
            }
            
            // Medicine reminders button
            btnMedicineReminders.setOnClickListener {
                // Navigate to reminders
                // For now, just show a toast
                Toast.makeText(this@MainActivity, R.string.medicine_reminders, Toast.LENGTH_SHORT).show()
            }
            
            // Settings button
            btnSettings.setOnClickListener {
                // Navigate to settings
                // For now, just show a toast
                Toast.makeText(this@MainActivity, R.string.settings, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupObservers() {
        // Observe any data changes if needed
    }
    
    private fun checkPermissionsAndOpenCamera() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        
        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        
        if (permissionsToRequest.isEmpty()) {
            openCamera()
        } else {
            requestPermissionLauncher.launch(permissionsToRequest)
        }
    }
    
    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh data if needed
    }
}