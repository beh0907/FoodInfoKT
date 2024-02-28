package com.skymilk.foodinfokt.repository

import androidx.lifecycle.LiveData
import com.skymilk.foodinfokt.db.MealDao
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.CategoryList
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.retrofit.MealApi

class MealRepositoryImpl(
    private val mealApi: MealApi,
    private val mealDao: MealDao
) : MealRepository {
    override suspend fun getRandomMeal(): Meal {
        return mealApi.getRandomMeal().body()!!.meals.first()
    }

    override suspend fun getDetailMeal(id: String): Meal {
        return mealApi.getDetailMeal(id).body()!!.meals.first()
    }

    override suspend fun getPopularItems(category: String): List<MealsByCategory> {
        return mealApi.getPopularItems(category).body()!!.meals
    }

    override suspend fun getCategories(): List<Category> {
        return mealApi.getCategories().body()!!.categories
    }

    override suspend fun getMealsByCategory(category: String): List<MealsByCategory> {
        return mealApi.getMealsByCategory(category).body()!!.meals
    }

    override suspend fun searchMealsByKeyword(keyword: String): List<Meal> {
        return mealApi.searchMealsByKeyword(keyword).body()!!.meals
    }

    override fun getAllFavoriteMeals(): LiveData<List<Meal>> {
        return mealDao.getAllFavoriteMeals()
    }

    override suspend fun insertFavoriteMeal(meal: Meal) {
        mealDao.upsert(meal)
    }

    override suspend fun deleteFavoriteMeal(meal: Meal) {
        mealDao.delete(meal)
    }
}