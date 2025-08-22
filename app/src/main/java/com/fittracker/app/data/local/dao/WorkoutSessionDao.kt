package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.WorkoutSession
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WorkoutSessionDao {
    
    @Query("SELECT * FROM workout_sessions WHERE userId = :userId ORDER BY startTime DESC")
    fun getWorkoutSessionsByUser(userId: String): Flow<List<WorkoutSession>>
    
    @Query("SELECT * FROM workout_sessions WHERE userId = :userId AND date(startTime) = date(:date)")
    fun getWorkoutSessionsByDate(userId: String, date: Date): Flow<List<WorkoutSession>>
    
    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId")
    suspend fun getWorkoutSessionById(sessionId: Long): WorkoutSession?
    
    @Query("SELECT * FROM workout_sessions WHERE userId = :userId AND isCompleted = 1 ORDER BY startTime DESC LIMIT :limit")
    fun getRecentCompletedWorkouts(userId: String, limit: Int = 10): Flow<List<WorkoutSession>>
    
    @Query("SELECT COUNT(*) FROM workout_sessions WHERE userId = :userId AND isCompleted = 1")
    suspend fun getCompletedWorkoutCount(userId: String): Int
    
    @Query("SELECT SUM(totalCalories) FROM workout_sessions WHERE userId = :userId AND date(startTime) = date(:date) AND isCompleted = 1")
    suspend fun getTotalCaloriesBurnedByDate(userId: String, date: Date): Float?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSession(session: WorkoutSession): Long
    
    @Update
    suspend fun updateWorkoutSession(session: WorkoutSession)
    
    @Delete
    suspend fun deleteWorkoutSession(session: WorkoutSession)
    
    @Query("DELETE FROM workout_sessions WHERE id = :sessionId")
    suspend fun deleteWorkoutSessionById(sessionId: Long)
}