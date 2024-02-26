package com.skymilk.foodinfokt.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.databinding.ActivityMealBinding
import com.skymilk.foodinfokt.fragments.HomeFragment
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.viewModels.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    //프래그먼트 Home View Model
    private val viewModel: MealViewModel by viewModels()

    private lateinit var meal: Meal // 현재 조회중인 음식 정보

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로딩 중 상태 UI 반영
        loadingState()

        getMealInfoWithIntent()
        setClick()
        setObserve()
    }

    private fun getMealInfoWithIntent() {
        val intent = intent

        //인텐트에 담긴 값을 가져와 저장한다
        val mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        val mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        val mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

        //인텐트로부터 가져온 데이터를 적용한다
        binding.toolbarCollapse.title = mealName
        Glide.with(this).load(mealThumb).into(binding.imgMealDetail) // 썸네일 적용

        //랜덤 음식 가져오기
        viewModel.getMealDetail(mealId)
    }


    //클릭 이벤트 설정 모음
    private fun setClick() {
        //유튜브 버튼 클릭
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(viewModel.mealDetailLiveData.value!!.strYoutube)
            )
            startActivity(intent)
        }

        //즐겨찾기 버튼 클릭
        binding.btnFavorite.setOnClickListener {
            //mealDetailLiveData null 체크를 위해 let
            viewModel.mealDetailLiveData.value?.let { meal ->
                viewModel.insertFavoriteMeal(meal)
                Toast.makeText(this, "음식 정보를 저장하였습니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    //옵저버 설정 모음
    private fun setObserve() {
        viewModel.mealDetailLiveData.observe(this) {
            //상세 정보 습득 완료 상태 UI 반영
            responseState()

            binding.txtCategory.text = "Category : ${it!!.strCategory}"
            binding.txtArea.text = "Area : ${it.strArea}"
            binding.txtInstructionsStep.text = it.strInstructions
        }
    }

    //로딩 상태
    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFavorite.visibility = View.INVISIBLE
        binding.txtCategory.visibility = View.INVISIBLE
        binding.txtArea.visibility = View.INVISIBLE
        binding.txtInstructionsStep.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    //로딩 완료 상태
    private fun responseState() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFavorite.visibility = View.VISIBLE
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtArea.visibility = View.VISIBLE
        binding.txtInstructionsStep.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}