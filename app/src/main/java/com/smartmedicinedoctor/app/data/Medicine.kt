package com.smartmedicinedoctor.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val dosage: String,
    val frequency: String,
    val duration: String,
    val instructions: String,
    val prescriptionId: Long,
    val extractedText: String = "",
    val aiExplanation: String = "",
    val sideEffects: String = "",
    val precautions: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable