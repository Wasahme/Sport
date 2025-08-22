package com.fittracker.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FitTrackerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // قناة عداد الخطوات
            val stepsChannel = NotificationChannel(
                CHANNEL_STEPS,
                "عداد الخطوات",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "إشعارات عداد الخطوات والتتبع"
                enableLights(false)
                enableVibration(false)
            }

            // قناة التمارين
            val workoutChannel = NotificationChannel(
                CHANNEL_WORKOUTS,
                "التمارين",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "إشعارات التمارين والتذكيرات"
                enableLights(true)
                enableVibration(true)
            }

            // قناة التغذية
            val nutritionChannel = NotificationChannel(
                CHANNEL_NUTRITION,
                "التغذية",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "إشعارات التغذية وتذكيرات الماء"
                enableLights(true)
                enableVibration(true)
            }

            // قناة الإنجازات
            val achievementsChannel = NotificationChannel(
                CHANNEL_ACHIEVEMENTS,
                "الإنجازات",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "إشعارات الإنجازات والشارات"
                enableLights(true)
                enableVibration(true)
            }

            notificationManager.createNotificationChannels(
                listOf(stepsChannel, workoutChannel, nutritionChannel, achievementsChannel)
            )
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    companion object {
        const val CHANNEL_STEPS = "steps_channel"
        const val CHANNEL_WORKOUTS = "workouts_channel"
        const val CHANNEL_NUTRITION = "nutrition_channel"
        const val CHANNEL_ACHIEVEMENTS = "achievements_channel"
    }
}