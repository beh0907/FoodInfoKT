package com.skymilk.foodinfokt.repository

import androidx.lifecycle.LiveData
import com.skymilk.foodinfokt.db.MealDao
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.retrofit.MealApi

class MealRepositoryImpl(
    private val mealApi: MealApi,
    private val mealDao: MealDao
) : MealRepository {
    override suspend fun getRandomMeal(): Meal {
        return try {
            mealApi.getRandomMeal().body()!!.meals.first()
        } catch (e: Exception) {
            e.printStackTrace()
            Meal()
        }
    }

    override suspend fun getDetailMeal(id: String): Meal {
        return try {
            mealApi.getDetailMeal(id).body()!!.meals.first()
        } catch (e: Exception) {
            e.printStackTrace()
            Meal()
        }
    }

    override suspend fun getPopularItems(category: String): List<MealsByCategory> {
        return try {
            mealApi.getPopularItems(category).body()!!.meals
        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf()
        }
    }

    override suspend fun getCategories(): List<Category> {
        return try {
            mealApi.getCategories().body()!!.categories
        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf()
        }
    }

    override suspend fun getMealsByCategory(category: String): List<MealsByCategory> {
        return try {
            mealApi.getMealsByCategory(category).body()!!.meals
        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf()
        }
    }

    override suspend fun searchMealsByKeyword(keyword: String): List<Meal> {
        return try {
            mealApi.searchMealsByKeyword(keyword).body()!!.meals
        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf()
        }
    }

    override fun getAllFavoriteMeals(): LiveData<List<Meal>> {
        return mealDao.getAllFavoriteMeals()
    }

    override suspend fun getIsFavoriteMeal(idMeal: String): Boolean {
        return mealDao.getIsFavoriteMeal(idMeal)
    }

    override suspend fun insertFavoriteMeal(meal: Meal): Long {
        return mealDao.upsert(meal)
    }

    override suspend fun deleteFavoriteMeal(meal: Meal): Int {
        return mealDao.delete(meal)
    }
}