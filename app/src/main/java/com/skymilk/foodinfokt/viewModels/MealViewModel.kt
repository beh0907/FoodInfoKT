package com.skymilk.foodinfokt.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.foodinfokt.db.MealDao
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealList
import com.skymilk.foodinfokt.retrofit.MealApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MealViewModel @Inject constructor(
    private val mealDao: MealDao,
    private val mealApi: MealApi
) : ViewModel() {
    val TAG = "CategoryMealsViewModel"

    var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String) {
        mealApi.getDetailMeal(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch { //코루틴 생성
            mealDao.upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch { //코루틴 생성
            mealDao.delete(meal)
        }
    }
}