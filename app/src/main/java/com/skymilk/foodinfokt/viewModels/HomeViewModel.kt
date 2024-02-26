package com.skymilk.foodinfokt.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.foodinfokt.db.MealDao
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.CategoryList
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealByCategoryList
import com.skymilk.foodinfokt.models.MealList
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealDao: MealDao,
    private val mealApi: MealApi
) : ViewModel() {
    val TAG = "HomeViewModel"

    var randomMealLiveData = MutableLiveData<Meal>()
    var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    var categoriesLiveData = MutableLiveData<List<Category>>()
    var bottomSheetMealLiveData = MutableLiveData<Meal>()
    var searchMealsLiveData = MutableLiveData<List<Meal>>()

    var favoriteMealsLiveDatabase = mealDao.getAllMeals()

    init {
        viewModelScope.launch {
            getRandomMeal()
        }
    }

    //무작위 음식 조회
    private suspend fun getRandomMeal() {
        //Retrofit 라이브러리
        val response = mealApi.getRandomMeal()

        if (!response.isSuccessful || response.body() == null) {
            Log.d(TAG, "getRandomMeal 통신 실패")
            return
        }

        val meal: Meal = response.body()!!.meals.first()
        randomMealLiveData.value = meal
        Log.d(TAG, meal.toString())
    }


    //음식 조건 검색
    fun getFilterMeal(category: String) {
        mealApi.getPopularItems(category).enqueue(object : Callback<MealByCategoryList> {
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

    //카테고리 목록 가져오기
    fun getCategories() {
        mealApi.getCategories().enqueue(object : Callback<CategoryList> {
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

    //음식 ID 정보로 상세 정보 조회
    fun getMealById(id: String) {
        mealApi.getDetailMeal(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal: Meal = response.body()!!.meals.first()

                meal.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }

    //키워드로 음식 검색
    fun searchMealsByKeyword(keyword: String) {
        mealApi.searchMealsByKeyword(keyword).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let {
                    searchMealsLiveData.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }

    //즐겨찾기 추가
    fun insertFavoriteMeal(meal: Meal) {
        viewModelScope.launch { //코루틴 생성
            mealDao.upsert(meal)
        }
    }

    //즐겨찾기 삭제
    fun deleteFavoriteMeal(meal: Meal) {
        viewModelScope.launch { //코루틴 생성
            mealDao.delete(meal)
        }
    }
}