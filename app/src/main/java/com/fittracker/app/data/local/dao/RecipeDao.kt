package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    
    @Query("SELECT * FROM recipes WHERE isActive = 1")
    fun getAllActiveRecipes(): Flow<List<Recipe>>
    
    @Query("SELECT * FROM recipes WHERE userId = :userId AND isActive = 1")
    fun getRecipesByUser(userId: String): Flow<List<Recipe>>
    
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Long): Recipe?
    
    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%' OR nameAr LIKE '%' || :query || '%'")
    fun searchRecipes(query: String): Flow<List<Recipe>>
    
    @Query("SELECT * FROM recipes WHERE isPublic = 1 AND isActive = 1")
    fun getPublicRecipes(): Flow<List<Recipe>>
    
    @Query("SELECT * FROM recipes WHERE userId = :userId AND isPublic = 0 AND isActive = 1")
    fun getPrivateRecipes(userId: String): Flow<List<Recipe>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long
    
    @Update
    suspend fun updateRecipe(recipe: Recipe)
    
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
    
    @Query("DELETE FROM recipes WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Long)
    
    @Query("SELECT COUNT(*) FROM recipes WHERE userId = :userId AND isActive = 1")
    suspend fun getUserRecipeCount(userId: String): Int
}