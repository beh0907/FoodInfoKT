package com.skymilk.foodinfokt.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryMealsViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {
    val TAG = "CategoryMealsViewModel"

    var mealsByCategoryLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {
            mealsByCategoryLiveData.postValue(mealRepository.getMealsByCategory(category))
        }
    }
}