package com.fittracker.app.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.fittracker.app.FitTrackerApplication
import com.fittracker.app.R
import com.fittracker.app.domain.model.StepData
import com.fittracker.app.domain.model.StepSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class StepCounterService : Service(), SensorEventListener {

    @Inject
    lateinit var stepDataDao: com.fittracker.app.data.local.dao.StepDataDao

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensor: Sensor
    
    private var currentSteps = 0
    private var lastStepCount = 0
    private var startTime = Date()
    private var isRunning = false

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "step_counter_channel"
        private const val ACTION_START = "START_STEP_COUNTING"
        private const val ACTION_STOP = "STOP_STEP_COUNTING"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        setupSensor()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startStepCounting()
            ACTION_STOP -> stopStepCounting()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "عداد الخطوات",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "إشعار عداد الخطوات"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setupSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    private fun startStepCounting() {
        if (isRunning) return
        
        isRunning = true
        startTime = Date()
        lastStepCount = 0
        
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        
        startForeground(NOTIFICATION_ID, createNotification())
    }

    private fun stopStepCounting() {
        if (!isRunning) return
        
        isRunning = false
        sensorManager.unregisterListener(this)
        
        // حفظ البيانات النهائية
        saveStepData()
        
        stopForeground(true)
        stopSelf()
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("عداد الخطوات يعمل")
            .setContentText("الخطوات: $currentSteps")
            .setSmallIcon(R.drawable.ic_steps)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val totalSteps = it.values[0].toInt()
                currentSteps = totalSteps - lastStepCount
                
                if (lastStepCount == 0) {
                    lastStepCount = totalSteps
                }
                
                updateNotification()
                
                // حفظ البيانات كل 10 خطوات
                if (currentSteps % 10 == 0) {
                    saveStepData()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // لا نحتاج لمعالجة تغيير الدقة
    }

    private fun saveStepData() {
        serviceScope.launch {
            try {
                val stepData = StepData(
                    userId = "default_user", // سيتم تحديثه لاحقاً
                    date = Date(),
                    steps = currentSteps,
                    distance = calculateDistance(currentSteps),
                    calories = calculateCalories(currentSteps),
                    activeMinutes = calculateActiveMinutes(),
                    sedentaryMinutes = 0,
                    startTime = startTime,
                    endTime = Date(),
                    source = StepSource.SENSOR
                )
                
                stepDataDao.insertStepData(stepData)
            } catch (e: Exception) {
                // معالجة الأخطاء
            }
        }
    }

    private fun calculateDistance(steps: Int): Float {
        // متوسط طول الخطوة 0.8 متر
        return steps * 0.8f
    }

    private fun calculateCalories(steps: Int): Float {
        // متوسط السعرات المحروقة لكل خطوة 0.04 سعرة
        return steps * 0.04f
    }

    private fun calculateActiveMinutes(): Int {
        val duration = (Date().time - startTime.time) / 1000 / 60 // بالدقائق
        return duration.toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopStepCounting()
    }
}