package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    
    @Query("SELECT * FROM workouts WHERE userId = :userId AND isActive = 1")
    fun getWorkoutsByUser(userId: String): Flow<List<Workout>>
    
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Long): Workout?
    
    @Query("SELECT * FROM workouts WHERE isTemplate = 1 AND isActive = 1")
    fun getTemplateWorkouts(): Flow<List<Workout>>
    
    @Query("SELECT * FROM workouts WHERE userId = :userId AND isTemplate = 0 AND isActive = 1")
    fun getUserWorkouts(userId: String): Flow<List<Workout>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long
    
    @Update
    suspend fun updateWorkout(workout: Workout)
    
    @Delete
    suspend fun deleteWorkout(workout: Workout)
    
    @Query("DELETE FROM workouts WHERE id = :workoutId")
    suspend fun deleteWorkoutById(workoutId: Long)
    
    @Query("SELECT COUNT(*) FROM workouts WHERE userId = :userId AND isActive = 1")
    suspend fun getUserWorkoutCount(userId: String): Int
}