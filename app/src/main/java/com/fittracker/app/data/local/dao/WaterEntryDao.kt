package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.WaterEntry
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WaterEntryDao {
    
    @Query("SELECT * FROM water_entries WHERE userId = :userId ORDER BY date DESC, time DESC")
    fun getWaterEntriesByUser(userId: String): Flow<List<WaterEntry>>
    
    @Query("SELECT * FROM water_entries WHERE userId = :userId AND date = :date ORDER BY time DESC")
    fun getWaterEntriesByDate(userId: String, date: Date): Flow<List<WaterEntry>>
    
    @Query("SELECT * FROM water_entries WHERE id = :entryId")
    suspend fun getWaterEntryById(entryId: Long): WaterEntry?
    
    @Query("SELECT SUM(amount) FROM water_entries WHERE userId = :userId AND date = :date")
    suspend fun getTotalWaterByDate(userId: String, date: Date): Float?
    
    @Query("SELECT SUM(amount) FROM water_entries WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalWaterByDateRange(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("SELECT AVG(amount) FROM water_entries WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getAverageWaterByDateRange(userId: String, startDate: Date, endDate: Date): Float?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterEntry(entry: WaterEntry): Long
    
    @Update
    suspend fun updateWaterEntry(entry: WaterEntry)
    
    @Delete
    suspend fun deleteWaterEntry(entry: WaterEntry)
    
    @Query("DELETE FROM water_entries WHERE id = :entryId")
    suspend fun deleteWaterEntryById(entryId: Long)
    
    @Query("DELETE FROM water_entries WHERE userId = :userId AND date = :date")
    suspend fun deleteWaterEntriesByDate(userId: String, date: Date)
}