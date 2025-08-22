package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.Exercise
import com.fittracker.app.domain.model.ExerciseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    
    @Query("SELECT * FROM exercises WHERE isActive = 1")
    fun getAllActiveExercises(): Flow<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE category = :category AND isActive = 1")
    fun getExercisesByCategory(category: ExerciseCategory): Flow<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: Long): Exercise?
    
    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :query || '%' OR nameAr LIKE '%' || :query || '%'")
    fun searchExercises(query: String): Flow<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE isCustom = 1 AND createdBy = :userId")
    fun getCustomExercises(userId: String): Flow<List<Exercise>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise): Long
    
    @Update
    suspend fun updateExercise(exercise: Exercise)
    
    @Delete
    suspend fun deleteExercise(exercise: Exercise)
    
    @Query("DELETE FROM exercises WHERE id = :exerciseId")
    suspend fun deleteExerciseById(exerciseId: Long)
    
    @Query("SELECT COUNT(*) FROM exercises WHERE isActive = 1")
    suspend fun getActiveExerciseCount(): Int
}