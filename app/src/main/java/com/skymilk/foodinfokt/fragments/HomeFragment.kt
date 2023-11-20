package com.skymilk.foodinfokt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.databinding.FragmentHomeBinding
import com.skymilk.foodinfokt.viewModels.HomeViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        //랜덤 음식 가져오기
        viewModel.getRandomMeal()

        //음식 가져왔을 때 옵저버
        viewModel.randomMealLiveData.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.strMealThumb).into(binding.imgRandomFood)
        }
    }
}