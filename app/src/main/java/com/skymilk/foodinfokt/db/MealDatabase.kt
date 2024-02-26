package com.skymilk.foodinfokt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skymilk.foodinfokt.models.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase : RoomDatabase() {

    abstract val mealDao: MealDao

}