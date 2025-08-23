package com.smartmedicinedoctor.app.utils

import android.content.Context
import androidx.work.*
import com.smartmedicinedoctor.app.data.MedicineReminder
import com.smartmedicinedoctor.app.worker.MedicineReminderWorker
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class ReminderScheduler(private val context: Context) {
    
    companion object {
        private const val REMINDER_WORK_TAG = "medicine_reminder"
    }
    
    fun scheduleReminder(reminder: MedicineReminder) {
        val workManager = WorkManager.getInstance(context)
        
        // Cancel existing reminders for this medicine
        cancelReminder(reminder.id)
        
        if (!reminder.isActive) return
        
        val timeParts = reminder.time.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()
        
        val days = reminder.days.split(",").map { it.trim().toInt() }
        
        // Schedule for each day
        days.forEach { day ->
            scheduleReminderForDay(reminder, day, hour, minute)
        }
    }
    
    private fun scheduleReminderForDay(reminder: MedicineReminder, day: Int, hour: Int, minute: Int) {
        val workManager = WorkManager.getInstance(context)
        
        // Create input data
        val inputData = Data.Builder()
            .putString(MedicineReminderWorker.KEY_MEDICINE_NAME, reminder.medicineName)
            .putString(MedicineReminderWorker.KEY_DOSAGE, reminder.dosage)
            .putLong(MedicineReminderWorker.KEY_REMINDER_ID, reminder.id)
            .build()
        
        // Create periodic work request
        val reminderWork = PeriodicWorkRequestBuilder<MedicineReminderWorker>(
            1, TimeUnit.DAYS
        )
        .setInputData(inputData)
        .addTag("${REMINDER_WORK_TAG}_${reminder.id}_$day")
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()
        )
        .build()
        
        workManager.enqueueUniquePeriodicWork(
            "${REMINDER_WORK_TAG}_${reminder.id}_$day",
            ExistingPeriodicWorkPolicy.REPLACE,
            reminderWork
        )
    }
    
    fun cancelReminder(reminderId: Long) {
        val workManager = WorkManager.getInstance(context)
        
        // Cancel all work with the reminder tag
        workManager.cancelAllWorkByTag("${REMINDER_WORK_TAG}_${reminderId}_1")
        workManager.cancelAllWorkByTag("${REMINDER_WORK_TAG}_${reminderId}_2")
        workManager.cancelAllWorkByTag("${REMINDER_WORK_TAG}_${reminderId}_3")
        workManager.cancelAllWorkByTag("${REMINDER_WORK_TAG}_${reminderId}_4")
        workManager.cancelAllWorkByTag("${REMINDER_WORK_TAG}_${reminderId}_5")
        workManager.cancelAllWorkByTag("${REMINDER_WORK_TAG}_${reminderId}_6")
        workManager.cancelAllWorkByTag("${REMINDER_WORK_TAG}_${reminderId}_7")
    }
    
    fun cancelAllReminders() {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(REMINDER_WORK_TAG)
    }
    
    fun scheduleOneTimeReminder(reminder: MedicineReminder, delayInMinutes: Long) {
        val workManager = WorkManager.getInstance(context)
        
        val inputData = Data.Builder()
            .putString(MedicineReminderWorker.KEY_MEDICINE_NAME, reminder.medicineName)
            .putString(MedicineReminderWorker.KEY_DOSAGE, reminder.dosage)
            .putLong(MedicineReminderWorker.KEY_REMINDER_ID, reminder.id)
            .build()
        
        val reminderWork = OneTimeWorkRequestBuilder<MedicineReminderWorker>()
            .setInputData(inputData)
            .setInitialDelay(delayInMinutes, TimeUnit.MINUTES)
            .addTag("${REMINDER_WORK_TAG}_one_time_${reminder.id}")
            .build()
        
        workManager.enqueue(reminderWork)
    }
}