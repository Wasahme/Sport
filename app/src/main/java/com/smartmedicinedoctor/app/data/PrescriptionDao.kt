package com.smartmedicinedoctor.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PrescriptionDao {
    
    @Query("SELECT * FROM prescriptions ORDER BY createdAt DESC")
    fun getAllPrescriptions(): Flow<List<Prescription>>
    
    @Query("SELECT * FROM prescriptions WHERE id = :id")
    suspend fun getPrescriptionById(id: Long): Prescription?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrescription(prescription: Prescription): Long
    
    @Update
    suspend fun updatePrescription(prescription: Prescription)
    
    @Delete
    suspend fun deletePrescription(prescription: Prescription)
    
    @Query("DELETE FROM prescriptions WHERE id = :id")
    suspend fun deletePrescriptionById(id: Long)
}