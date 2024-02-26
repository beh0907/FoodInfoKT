package com.skymilk.foodinfokt.repository

import com.skymilk.foodinfokt.db.MealDao
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.retrofit.MealApi

class MealRepositoryImpl(
    private val mealApi: MealApi,
    private val mealDao: MealDao
) : MealRepository {
    override suspend fun insertFavoriteMeal(meal: Meal) {
        mealDao.upsert(meal)
    }

    override suspend fun deleteFavoriteMeal(meal: Meal) {
        mealDao.delete(meal)
    }
}