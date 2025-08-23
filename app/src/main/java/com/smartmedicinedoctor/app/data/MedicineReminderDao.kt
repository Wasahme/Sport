package com.smartmedicinedoctor.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineReminderDao {
    
    @Query("SELECT * FROM medicine_reminders ORDER BY time ASC")
    fun getAllReminders(): Flow<List<MedicineReminder>>
    
    @Query("SELECT * FROM medicine_reminders WHERE isActive = 1 ORDER BY time ASC")
    fun getActiveReminders(): Flow<List<MedicineReminder>>
    
    @Query("SELECT * FROM medicine_reminders WHERE id = :id")
    suspend fun getReminderById(id: Long): MedicineReminder?
    
    @Query("SELECT * FROM medicine_reminders WHERE medicineId = :medicineId")
    fun getRemindersByMedicineId(medicineId: Long): Flow<List<MedicineReminder>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: MedicineReminder): Long
    
    @Update
    suspend fun updateReminder(reminder: MedicineReminder)
    
    @Delete
    suspend fun deleteReminder(reminder: MedicineReminder)
    
    @Query("DELETE FROM medicine_reminders WHERE medicineId = :medicineId")
    suspend fun deleteRemindersByMedicineId(medicineId: Long)
    
    @Query("UPDATE medicine_reminders SET isActive = :isActive WHERE id = :id")
    suspend fun updateReminderStatus(id: Long, isActive: Boolean)
}