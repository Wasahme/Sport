package com.smartmedicinedoctor.app.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smartmedicinedoctor.app.R
import com.smartmedicinedoctor.app.SmartMedicineDoctorApp
import com.smartmedicinedoctor.app.ui.MedicineReminderActivity
import com.smartmedicinedoctor.app.utils.TextToSpeechHelper

class MedicineReminderWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    companion object {
        const val KEY_MEDICINE_NAME = "medicine_name"
        const val KEY_DOSAGE = "dosage"
        const val KEY_REMINDER_ID = "reminder_id"
    }
    
    override suspend fun doWork(): Result {
        val medicineName = inputData.getString(KEY_MEDICINE_NAME) ?: return Result.failure()
        val dosage = inputData.getString(KEY_DOSAGE) ?: return Result.failure()
        val reminderId = inputData.getLong(KEY_REMINDER_ID, -1)
        
        try {
            // Show notification
            showNotification(medicineName, dosage, reminderId)
            
            // Play sound reminder
            playSoundReminder(medicineName, dosage)
            
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
    
    private fun showNotification(medicineName: String, dosage: String, reminderId: Long) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                SmartMedicineDoctorApp.MEDICINE_REMINDER_CHANNEL_ID,
                SmartMedicineDoctorApp.MEDICINE_REMINDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = SmartMedicineDoctorApp.MEDICINE_REMINDER_CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        // Create intent for notification tap
        val intent = Intent(context, MedicineReminderActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(KEY_REMINDER_ID, reminderId)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            reminderId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Build notification
        val notification = NotificationCompat.Builder(context, SmartMedicineDoctorApp.MEDICINE_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_medicine)
            .setContentTitle(context.getString(R.string.medicine_reminder_title))
            .setContentText("$medicineName - $dosage")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .build()
        
        notificationManager.notify(reminderId.toInt(), notification)
    }
    
    private fun playSoundReminder(medicineName: String, dosage: String) {
        val ttsHelper = TextToSpeechHelper(context)
        ttsHelper.speakMedicineReminder(medicineName, dosage)
    }
}