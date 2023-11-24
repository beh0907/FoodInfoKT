package com.skymilk.foodinfokt.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skymilk.foodinfokt.activities.MainActivity
import com.skymilk.foodinfokt.activities.MealActivity
import com.skymilk.foodinfokt.databinding.FragmentMealBottomSheetBinding
import com.skymilk.foodinfokt.fragments.HomeFragment
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.viewModels.HomeViewModel

private const val MEAL_ID = "MEAL_ID"

class MealBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    private var mealId: String? = null
    private var mealName:String? = null
    private var mealThumb:String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        //프래그먼트 생성 시 뷰 모델 초기화
        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }

        setClick()
        setObserve()
    }

    private fun setClick() {
        binding.layoutBottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(context, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)

                }
                //화면 이동 후 현재 표시된 화면 종료
                startActivity(intent)
                dismiss()
            }
        }
    }

    private fun setObserve() {
        viewModel.bottomSheetMealLiveData.observe(this) {
            Glide.with(this).load(it.strMealThumb).into(binding.imgMeal)
            binding.txtCategory.text = it.strCategory
            binding.txtArea.text = it.strArea
            binding.txtMealName.text = it.strMeal

            mealName = it.strMeal
            mealThumb = it.strMealThumb
        }
    }
}