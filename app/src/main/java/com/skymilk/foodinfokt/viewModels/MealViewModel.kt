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
    var isFavoriteLiveData = MutableLiveData<Boolean>()

    fun getMealDetail(id: String) {
        viewModelScope.launch {
            mealDetailLiveData.value = mealRepository.getDetailMeal(id)
        }
    }

    fun getIsFavoriteMeal(id: String) {
        viewModelScope.launch {
            isFavoriteLiveData.value = mealRepository.getIsFavoriteMeal(id)
        }
    }


    fun insertFavoriteMeal(meal: Meal) {
        viewModelScope.launch {
            val result: Long = mealRepository.insertFavoriteMeal(meal)

            //DB 삽입에 성공했다면 체크
            if (result > 0) isFavoriteLiveData.value = true
        }
    }

    fun deleteFavoriteMeal(meal: Meal) {
        viewModelScope.launch {
            val result: Int = mealRepository.deleteFavoriteMeal(meal)

            //DB 삭제에 성공했다면 체크
            if (result > 0) isFavoriteLiveData.value = false
        }
    }
}