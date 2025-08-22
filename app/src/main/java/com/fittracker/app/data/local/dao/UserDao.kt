package com.fittracker.app.data.local.dao

import androidx.room.*
import com.fittracker.app.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?
    
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
    
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    @Update
    suspend fun updateUser(user: User)
    
    @Delete
    suspend fun deleteUser(user: User)
    
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: String)
    
    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
    
    @Query("SELECT * FROM users ORDER BY lastLoginAt DESC LIMIT 1")
    suspend fun getLastLoggedInUser(): User?
}