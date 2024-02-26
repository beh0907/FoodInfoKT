package com.skymilk.foodinfokt.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skymilk.foodinfokt.models.MealByCategoryList
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
        mealApi.getMealsByCategory(category).enqueue(object : Callback<MealByCategoryList> {
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