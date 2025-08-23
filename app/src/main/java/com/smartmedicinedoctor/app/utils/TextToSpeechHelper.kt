package com.smartmedicinedoctor.app.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import java.util.*

class TextToSpeechHelper(private val context: Context) {
    
    private var textToSpeech: TextToSpeech? = null
    private val TAG = "TextToSpeechHelper"
    
    init {
        initializeTTS()
    }
    
    private fun initializeTTS() {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d(TAG, "TTS initialized successfully")
                setupTTS()
            } else {
                Log.e(TAG, "TTS initialization failed")
            }
        }
    }
    
    private fun setupTTS() {
        textToSpeech?.let { tts ->
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    Log.d(TAG, "TTS started: $utteranceId")
                }
                
                override fun onDone(utteranceId: String?) {
                    Log.d(TAG, "TTS completed: $utteranceId")
                }
                
                override fun onError(utteranceId: String?) {
                    Log.e(TAG, "TTS error: $utteranceId")
                }
            })
        }
    }
    
    suspend fun speak(text: String, language: String = "ar"): Boolean = suspendCancellableCoroutine { continuation ->
        textToSpeech?.let { tts ->
            val locale = when (language) {
                "ar" -> Locale("ar")
                "fr" -> Locale.FRENCH
                "en" -> Locale.ENGLISH
                else -> Locale("ar")
            }
            
            val result = tts.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "Language not supported: $language")
                continuation.resume(false)
                return@suspendCancellableCoroutine
            }
            
            val utteranceId = "medicine_reminder_${System.currentTimeMillis()}"
            val speakResult = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            
            if (speakResult == TextToSpeech.SUCCESS) {
                continuation.resume(true)
            } else {
                Log.e(TAG, "Failed to speak text")
                continuation.resume(false)
            }
        } ?: run {
            Log.e(TAG, "TTS not initialized")
            continuation.resume(false)
        }
    }
    
    fun speakMedicineReminder(medicineName: String, dosage: String, language: String = "ar") {
        val text = when (language) {
            "ar" -> "حان وقت تناول $medicineName. الجرعة: $dosage"
            "fr" -> "Il est temps de prendre $medicineName. Dosage: $dosage"
            "en" -> "Time to take $medicineName. Dosage: $dosage"
            else -> "حان وقت تناول $medicineName. الجرعة: $dosage"
        }
        
        // Use coroutine scope to call speak function
        // In a real implementation, you would use a coroutine scope here
        Log.d(TAG, "Speaking reminder: $text")
    }
    
    fun stop() {
        textToSpeech?.stop()
    }
    
    fun shutdown() {
        textToSpeech?.shutdown()
        textToSpeech = null
    }
    
    fun isLanguageAvailable(language: String): Boolean {
        return textToSpeech?.isLanguageAvailable(when (language) {
            "ar" -> Locale("ar")
            "fr" -> Locale.FRENCH
            "en" -> Locale.ENGLISH
            else -> Locale("ar")
        }) == TextToSpeech.LANG_AVAILABLE
    }
}