package com.skymilk.foodinfokt.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@ProvidedTypeConverter
class MealTypeConvertor {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        if (attribute == null) return ""

        return attribute as String
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        if (attribute == null) return ""

        return attribute
    }
}