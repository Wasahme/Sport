package com.smartmedicinedoctor.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager

class SmartMedicineDoctorApp : Application(), Configuration.Provider {
    
    companion object {
        const val MEDICINE_REMINDER_CHANNEL_ID = "medicine_reminder_channel"
        const val MEDICINE_REMINDER_CHANNEL_NAME = "تذكيرات الدواء"
        const val MEDICINE_REMINDER_CHANNEL_DESCRIPTION = "تذكيرات لتناول الدواء في الوقت المحدد"
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        initializeWorkManager()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val medicineReminderChannel = NotificationChannel(
                MEDICINE_REMINDER_CHANNEL_ID,
                MEDICINE_REMINDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = MEDICINE_REMINDER_CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(medicineReminderChannel)
        }
    }
    
    private fun initializeWorkManager() {
        // WorkManager will be initialized automatically
    }
    
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
}