package com.skymilk.foodinfokt.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skymilk.foodinfokt.db.MealDatabase
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.CategoryList
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.models.MealByCategoryList
import com.skymilk.foodinfokt.models.MealList
import com.skymilk.foodinfokt.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {
    val TAG = "HomeViewModel"

    var randomMealLiveData = MutableLiveData<Meal>()
    var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    var categoriesLiveData = MutableLiveData<List<Category>>()

    var favoriteMealsLiveDatabase = mealDatabase.mealDao().getAllMeals()

    //무작위 음식 조회
    fun getRandomMeal() {
        //Retrofit 라이브러리
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

    fun getFilterMeal(category: String) {
        RetrofitInstance.api.getPopularItems(category)
            .enqueue(object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                        Log.d(TAG, "getFilterMeal 성공")
                    }
                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                }
            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
                    categoriesLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }
}