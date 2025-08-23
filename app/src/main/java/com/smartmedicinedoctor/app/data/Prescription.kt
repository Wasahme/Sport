package com.smartmedicinedoctor.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Entity(tableName = "prescriptions")
data class Prescription(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imagePath: String,
    val extractedText: String,
    val doctorName: String = "",
    val patientName: String = "",
    val date: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable