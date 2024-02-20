package com.skymilk.foodinfokt.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.R
import com.skymilk.foodinfokt.activities.CategoryMealsActivity
import com.skymilk.foodinfokt.activities.MainActivity
import com.skymilk.foodinfokt.activities.MealActivity
import com.skymilk.foodinfokt.adapters.CategoriesAdapter
import com.skymilk.foodinfokt.adapters.PopularAdapter
import com.skymilk.foodinfokt.databinding.FragmentHomeBinding
import com.skymilk.foodinfokt.fragments.bottomSheet.MealBottomSheetFragment
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.MealsByCategory
import com.skymilk.foodinfokt.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    //리스트 어댑터
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.skymilk.foodinfokt.fragments.idMeal"
        const val MEAL_NAME = "com.skymilk.foodinfokt.fragments.nameMeal"
        const val MEAL_THUMB = "com.skymilk.foodinfokt.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.skymilk.foodinfokt.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //프래그먼트 생성 시 뷰 모델 초기화
        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPopularRecyclerView()
        initCategoryRecyclerView()

        setObserve()
        setClick()

        //필터링 음식 가져오기
        viewModel.getFilterMeal("seafood") // seafood로 일단 고정

        //카테고리 목록 가져오기
        viewModel.getCategories()
    }

    //popular 목록 초기화
    private fun initPopularRecyclerView() {
        //어댑터 초기화
        popularAdapter = PopularAdapter()

        //아이템 터치 이벤트 발생 시 동작
        popularAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }

        //아이템 롱 터치 이벤트 발생 시 동작
        popularAdapter.onItemLongClick = {
            //바텀 시트 정보 표시
            val bottomSheetFragment = MealBottomSheetFragment.newInstance(it.idMeal)
            bottomSheetFragment.show(childFragmentManager, "Meal Info")
        }


        //리사이클러뷰 설정
        binding.recyclerPopularFood.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun initCategoryRecyclerView() {
        //어댑터 초기화
        categoriesAdapter = CategoriesAdapter()

        //아이템 터치 이벤트 발생 시 동작
        categoriesAdapter.onItemClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }

        //리사이클러뷰 설정
        binding.recyclerCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    //클릭 이벤트 설정 모음
    private fun setClick() {
        binding.cardRandomFood.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, viewModel.randomMealLiveData.value!!.idMeal)
            intent.putExtra(MEAL_NAME, viewModel.randomMealLiveData.value!!.strMeal)
            intent.putExtra(MEAL_THUMB, viewModel.randomMealLiveData.value!!.strMealThumb)
            startActivity(intent)
        }

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }

    //옵저버 설정 모음
    private fun setObserve() {
        //랜덤 음식 하나를 가져왔을 때
        viewModel.randomMealLiveData.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.strMealThumb).into(binding.imgRandomFood)
        }

        //인기있는 음식들을 가져왔을 때
        viewModel.popularItemsLiveData.observe(viewLifecycleOwner) {
            popularAdapter.setMealList(it as ArrayList<MealsByCategory>)
        }

        //카테고리 목록을 가져왔을 때
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            categoriesAdapter.setCategoryList(it as ArrayList<Category>)
        }
    }
}