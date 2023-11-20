package com.skymilk.foodinfokt.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealList
import com.skymilk.foodinfokt.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    val TAG = "HomeViewModel"
    var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal() {
        //Retrofit 라이브러리
        //getRandomMeal API호출
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val meal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = meal
                    Log.d(TAG, meal.toString())
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }
}