package com.smartmedicinedoctor.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class OCRProcessor(private val context: Context) {
    companion object {
        private const val TAG = "OCRProcessor"
    }

    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun extractTextFromImage(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        try {
            val image = InputImage.fromBitmap(bitmap, 0)
            
            return@withContext suspendCancellableCoroutine { continuation ->
                textRecognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        val extractedText = visionText.text
                        Log.d(TAG, "Extracted text: $extractedText")
                        continuation.resume(extractedText)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error extracting text from image", e)
                        continuation.resume("")
                    }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error processing image", e)
            return@withContext ""
        }
    }

    fun release() {
        try {
            textRecognizer.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing text recognizer", e)
        }
    }
}