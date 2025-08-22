package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.StepData
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface StepDataDao {
    
    @Query("SELECT * FROM step_data WHERE userId = :userId AND date = :date")
    suspend fun getStepDataByDate(userId: String, date: Date): StepData?
    
    @Query("SELECT * FROM step_data WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getStepDataByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<StepData>>
    
    @Query("SELECT * FROM step_data WHERE userId = :userId ORDER BY date DESC LIMIT :limit")
    fun getRecentStepData(userId: String, limit: Int = 30): Flow<List<StepData>>
    
    @Query("SELECT SUM(steps) FROM step_data WHERE userId = :userId AND date = :date")
    suspend fun getTotalStepsByDate(userId: String, date: Date): Int?
    
    @Query("SELECT SUM(steps) FROM step_data WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalStepsByDateRange(userId: String, startDate: Date, endDate: Date): Int?
    
    @Query("SELECT SUM(distance) FROM step_data WHERE userId = :userId AND date = :date")
    suspend fun getTotalDistanceByDate(userId: String, date: Date): Float?
    
    @Query("SELECT SUM(calories) FROM step_data WHERE userId = :userId AND date = :date")
    suspend fun getTotalCaloriesByDate(userId: String, date: Date): Float?
    
    @Query("SELECT AVG(steps) FROM step_data WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getAverageStepsByDateRange(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("SELECT MAX(steps) FROM step_data WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getMaxStepsByDateRange(userId: String, startDate: Date, endDate: Date): Int?
    
    @Query("SELECT date, SUM(steps) as totalSteps FROM step_data WHERE userId = :userId AND date BETWEEN :startDate AND :endDate GROUP BY date ORDER BY date DESC")
    fun getStepSummaryByDateRange(userId: String, startDate: Date, endDate: Date): Flow<List<StepSummary>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStepData(stepData: StepData)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStepDataList(stepDataList: List<StepData>)
    
    @Update
    suspend fun updateStepData(stepData: StepData)
    
    @Delete
    suspend fun deleteStepData(stepData: StepData)
    
    @Query("DELETE FROM step_data WHERE userId = :userId AND date = :date")
    suspend fun deleteStepDataByDate(userId: String, date: Date)
    
    @Query("SELECT COUNT(*) FROM step_data WHERE userId = :userId AND isSynced = 0")
    suspend fun getUnsyncedStepDataCount(userId: String): Int
    
    @Query("UPDATE step_data SET isSynced = 1 WHERE userId = :userId AND date = :date")
    suspend fun markStepDataAsSynced(userId: String, date: Date)
}

data class StepSummary(
    val date: Date,
    val totalSteps: Int
)