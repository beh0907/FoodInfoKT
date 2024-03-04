package com.skymilk.foodinfokt.repository

import androidx.lifecycle.LiveData
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealsByCategory

interface MealRepository {

    //Retrofit2
    //////////////////////////////////////////////////////////////////////////////
    suspend fun getRandomMeal(): Meal
    suspend fun getDetailMeal(id: String): Meal
    suspend fun getPopularItems(category: String): List<MealsByCategory>
    suspend fun getCategories(): List<Category>
    suspend fun getMealsByCategory(category: String): List<MealsByCategory>
    suspend fun searchMealsByKeyword(keyword: String): List<Meal>
    //////////////////////////////////////////////////////////////////////////////


    //Room DB
    //////////////////////////////////////////////////////////////////////////////
    fun getAllFavoriteMeals(): LiveData<List<Meal>>
    suspend fun getIsFavoriteMeal(idMeal: String): Boolean
    suspend fun insertFavoriteMeal(meal: Meal): Long
    suspend fun deleteFavoriteMeal(meal: Meal): Int
    //////////////////////////////////////////////////////////////////////////////
}