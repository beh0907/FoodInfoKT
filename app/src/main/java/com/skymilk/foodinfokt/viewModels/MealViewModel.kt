package com.skymilk.foodinfokt.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {
    val TAG = "CategoryMealsViewModel"

    var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String) {
        viewModelScope.launch {
            mealDetailLiveData.value = mealRepository.getDetailMeal(id)
        }
    }

    fun insertFavoriteMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.insertFavoriteMeal(meal)
        }
    }

    fun deleteFavoriteMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.deleteFavoriteMeal(meal)
        }
    }
}