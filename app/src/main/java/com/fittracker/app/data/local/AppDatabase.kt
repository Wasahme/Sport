package com.fittracker.app.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fittracker.app.data.local.converter.DateConverter
import com.fittracker.app.data.local.converter.ListConverter
import com.fittracker.app.data.local.dao.*
import com.fittracker.app.domain.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Database(
    entities = [
        User::class,
        StepData::class,
        Exercise::class,
        Workout::class,
        WorkoutSession::class,
        Food::class,
        FoodEntry::class,
        WaterEntry::class,
        Recipe::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    // DAOs
    abstract fun userDao(): UserDao
    abstract fun stepDataDao(): StepDataDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun foodDao(): FoodDao
    abstract fun foodEntryDao(): FoodEntryDao
    abstract fun waterEntryDao(): WaterEntryDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        const val DATABASE_NAME = "fittracker_db"
    }
}

@Singleton
class DatabaseModule @Inject constructor(
    @ApplicationContext private val context: android.content.Context
) {
    
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }
}