package com.smartmedicinedoctor.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Entity(tableName = "medicine_reminders")
data class MedicineReminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val medicineId: Long,
    val medicineName: String,
    val dosage: String,
    val time: String, // Format: HH:mm
    val days: String, // Comma separated days: 1,2,3,4,5,6,7
    val isActive: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable