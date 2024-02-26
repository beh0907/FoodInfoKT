package com.skymilk.foodinfokt.repository

import com.skymilk.foodinfokt.models.Meal

interface MealRepository {


    suspend fun insertFavoriteMeal(meal: Meal)

    suspend fun deleteFavoriteMeal(meal: Meal)
}