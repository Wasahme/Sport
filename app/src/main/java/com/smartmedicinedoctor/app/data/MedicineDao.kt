package com.smartmedicinedoctor.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    
    @Query("SELECT * FROM medicines WHERE prescriptionId = :prescriptionId")
    fun getMedicinesByPrescriptionId(prescriptionId: Long): Flow<List<Medicine>>
    
    @Query("SELECT * FROM medicines WHERE id = :id")
    suspend fun getMedicineById(id: Long): Medicine?
    
    @Query("SELECT * FROM medicines ORDER BY createdAt DESC")
    fun getAllMedicines(): Flow<List<Medicine>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<Medicine>)
    
    @Update
    suspend fun updateMedicine(medicine: Medicine)
    
    @Delete
    suspend fun deleteMedicine(medicine: Medicine)
    
    @Query("DELETE FROM medicines WHERE prescriptionId = :prescriptionId")
    suspend fun deleteMedicinesByPrescriptionId(prescriptionId: Long)
    
    @Query("SELECT * FROM medicines WHERE name LIKE '%' || :query || '%'")
    suspend fun searchMedicines(query: String): List<Medicine>
}