package com.skymilk.foodinfokt.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    var randomMealLiveData = MutableLiveData<Meal>()
    var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    var categoriesLiveData = MutableLiveData<List<Category>>()
    var bottomSheetMealLiveData = MutableLiveData<Meal>()
    var searchMealsLiveData = MutableLiveData<List<Meal>>()

    var favoriteMealsLiveDatabase = mealRepository.getAllFavoriteMeals()

    init {
        //랜덤 음식 가져오기
        getRandomMeal()

        //필터링 음식 가져오기
        getFilterMeal("seafood") // seafood로 일단 고정

        //카테고리 목록 가져오기
        getCategories()
    }

    //무작위 음식 조회
    fun getRandomMeal() {
        viewModelScope.launch {
            randomMealLiveData.postValue(mealRepository.getRandomMeal())
        }
    }


    //음식 조건 검색
    fun getFilterMeal(category: String) {
        viewModelScope.launch {
            popularItemsLiveData.postValue(mealRepository.getPopularItems(category))
        }
    }

    //카테고리 목록 가져오기
    fun getCategories() {
        viewModelScope.launch {
            categoriesLiveData.postValue(mealRepository.getCategories())
        }
    }

    //음식 ID 정보로 상세 정보 조회
    fun getMealById(id: String) {
        viewModelScope.launch {
            bottomSheetMealLiveData.postValue(mealRepository.getDetailMeal(id))
        }
    }

    //키워드로 음식 검색
    fun searchMealsByKeyword(keyword: String) {
        viewModelScope.launch {
            searchMealsLiveData.postValue(mealRepository.searchMealsByKeyword(keyword))
        }
    }

    //즐겨찾기 추가
    fun insertFavoriteMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.insertFavoriteMeal(meal)
        }
    }

    //즐겨찾기 삭제
    fun deleteFavoriteMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.deleteFavoriteMeal(meal)
        }
    }
}