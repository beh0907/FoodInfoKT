package com.skymilk.foodinfokt.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.skymilk.foodinfokt.adapters.CategoryMealsAdapter
import com.skymilk.foodinfokt.databinding.ActivityCategoryMealsBinding
import com.skymilk.foodinfokt.fragments.HomeFragment
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.viewModels.CategoryMealsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private val viewModel: CategoryMealsViewModel by viewModels()

    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategoryInfoWithIntent()

        initPopularRecyclerView()

        setObserve()

    }

    private fun getCategoryInfoWithIntent() {
        val intent = intent

        //인텐트에 담긴 값을 가져와 저장한다
        val categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!

        //선택한 카테고리 음식 가져오기
        viewModel.getMealsByCategory(categoryName)
    }

    //popular 목록 초기화
    private fun initPopularRecyclerView() {
        //어댑터 초기화
        categoryMealsAdapter = CategoryMealsAdapter()

        //아이템 터치 이벤트 발생 시 동작
        categoryMealsAdapter.onItemClick = {
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }

        //리사이클러뷰 설정
        binding.recyclerMeals.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }


    //옵저버 설정 모음
    private fun setObserve() {
        //랜덤 음식 하나를 가져왔을 때
        viewModel.mealsByCategoryLiveData.observe(this) {
            //목록 사이즈 텍스트 표시
            binding.txtCategoryCnt.text = "${it.size}"
            categoryMealsAdapter.setMealList(it as ArrayList<MealsByCategory>)
        }
    }
}