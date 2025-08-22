package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.Food
import com.fittracker.app.domain.model.FoodCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    
    @Query("SELECT * FROM foods WHERE isActive = 1")
    fun getAllActiveFoods(): Flow<List<Food>>
    
    @Query("SELECT * FROM foods WHERE category = :category AND isActive = 1")
    fun getFoodsByCategory(category: FoodCategory): Flow<List<Food>>
    
    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFoodById(foodId: Long): Food?
    
    @Query("SELECT * FROM foods WHERE barcode = :barcode AND isActive = 1")
    suspend fun getFoodByBarcode(barcode: String): Food?
    
    @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%' OR nameAr LIKE '%' || :query || '%' OR brand LIKE '%' || :query || '%'")
    fun searchFoods(query: String): Flow<List<Food>>
    
    @Query("SELECT * FROM foods WHERE isCustom = 1 AND createdBy = :userId")
    fun getCustomFoods(userId: String): Flow<List<Food>>
    
    @Query("SELECT * FROM foods WHERE brand = :brand AND isActive = 1")
    fun getFoodsByBrand(brand: String): Flow<List<Food>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food): Long
    
    @Update
    suspend fun updateFood(food: Food)
    
    @Delete
    suspend fun deleteFood(food: Food)
    
    @Query("DELETE FROM foods WHERE id = :foodId")
    suspend fun deleteFoodById(foodId: Long)
    
    @Query("SELECT COUNT(*) FROM foods WHERE isActive = 1")
    suspend fun getActiveFoodCount(): Int
}