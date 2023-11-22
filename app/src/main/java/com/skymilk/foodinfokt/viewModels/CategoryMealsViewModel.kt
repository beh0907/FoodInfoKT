package com.skymilk.foodinfokt.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skymilk.foodinfokt.models.MealByCategoryList
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CategoryMealsViewModel() : ViewModel() {
    val TAG = "CategoryMealsViewModel"

    var mealsByCategoryLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(category: String) {
        RetrofitInstance.api.getMealsByCategory(category).enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                response.body()?.let {
                    mealsByCategoryLiveData.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }
}