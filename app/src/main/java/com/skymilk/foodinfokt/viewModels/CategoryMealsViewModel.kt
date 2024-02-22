package com.skymilk.foodinfokt.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryMealsViewModel @Inject constructor(
    private val mealApi: MealApi
) : ViewModel() {
    val TAG = "CategoryMealsViewModel"

    var mealsByCategoryLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(category: String) {
        mealApi.getMealsByCategory(category).enqueue(object : Callback<List<MealsByCategory>> {
            override fun onResponse(
                call: Call<List<MealsByCategory>>,
                response: Response<List<MealsByCategory>>
            ) {
                response.body()?.let {
                    mealsByCategoryLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<List<MealsByCategory>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }
}