package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.FoodEntry
import com.fittracker.app.domain.model.MealType
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface FoodEntryDao {
    
    @Query("SELECT * FROM food_entries WHERE userId = :userId ORDER BY date DESC, time DESC")
    fun getFoodEntriesByUser(userId: String): Flow<List<FoodEntry>>
    
    @Query("SELECT * FROM food_entries WHERE userId = :userId AND date = :date ORDER BY time DESC")
    fun getFoodEntriesByDate(userId: String, date: Date): Flow<List<FoodEntry>>
    
    @Query("SELECT * FROM food_entries WHERE userId = :userId AND mealType = :mealType AND date = :date ORDER BY time DESC")
    fun getFoodEntriesByMealType(userId: String, mealType: MealType, date: Date): Flow<List<FoodEntry>>
    
    @Query("SELECT * FROM food_entries WHERE id = :entryId")
    suspend fun getFoodEntryById(entryId: Long): FoodEntry?
    
    @Query("SELECT SUM(calories) FROM food_entries WHERE userId = :userId AND date = :date")
    suspend fun getTotalCaloriesByDate(userId: String, date: Date): Float?
    
    @Query("SELECT SUM(protein) FROM food_entries WHERE userId = :userId AND date = :date")
    suspend fun getTotalProteinByDate(userId: String, date: Date): Float?
    
    @Query("SELECT SUM(carbs) FROM food_entries WHERE userId = :userId AND date = :date")
    suspend fun getTotalCarbsByDate(userId: String, date: Date): Float?
    
    @Query("SELECT SUM(fat) FROM food_entries WHERE userId = :userId AND date = :date")
    suspend fun getTotalFatByDate(userId: String, date: Date): Float?
    
    @Query("SELECT SUM(fiber) FROM food_entries WHERE userId = :userId AND date = :date")
    suspend fun getTotalFiberByDate(userId: String, date: Date): Float?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodEntry(entry: FoodEntry): Long
    
    @Update
    suspend fun updateFoodEntry(entry: FoodEntry)
    
    @Delete
    suspend fun deleteFoodEntry(entry: FoodEntry)
    
    @Query("DELETE FROM food_entries WHERE id = :entryId")
    suspend fun deleteFoodEntryById(entryId: Long)
    
    @Query("DELETE FROM food_entries WHERE userId = :userId AND date = :date")
    suspend fun deleteFoodEntriesByDate(userId: String, date: Date)
}