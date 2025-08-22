package com.fittracker.app.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    
    private val gson = Gson()
    
    @TypeConverter
    fun fromString(value: String?): List<String> {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromList(list: List<String>?): String {
        if (list == null) return "[]"
        return gson.toJson(list)
    }
    
    @TypeConverter
    fun fromEquipmentList(value: String?): List<com.fittracker.app.domain.model.Equipment> {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<com.fittracker.app.domain.model.Equipment>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromEquipmentList(list: List<com.fittracker.app.domain.model.Equipment>?): String {
        if (list == null) return "[]"
        return gson.toJson(list)
    }
    
    @TypeConverter
    fun fromMuscleGroupList(value: String?): List<com.fittracker.app.domain.model.MuscleGroup> {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<com.fittracker.app.domain.model.MuscleGroup>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromMuscleGroupList(list: List<com.fittracker.app.domain.model.MuscleGroup>?): String {
        if (list == null) return "[]"
        return gson.toJson(list)
    }
}