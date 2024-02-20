package com.skymilk.foodinfokt.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.skymilk.foodinfokt.activities.MainActivity
import com.skymilk.foodinfokt.activities.MealActivity
import com.skymilk.foodinfokt.adapters.MealsAdapter
import com.skymilk.foodinfokt.databinding.FragmentSearchBinding
import com.skymilk.foodinfokt.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchRecyclerView()

        setSearchListener()
        setObserve()
    }

    private fun setSearchListener() {
        //텍스트가 입력될 때마다 일정 딜레이 후 검색
        binding.editSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                //키워드가 공백이 아닐 경우 검색 수행
                if (query!!.isNotEmpty()) viewModel.searchMealsByKeyword(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                //키워드가 공백이 아닐 경우 검색 수행
                if (newText!!.isNotEmpty()) viewModel.searchMealsByKeyword(newText)
                return true
            }

        })
    }

    private fun initSearchRecyclerView() {
        searchAdapter = MealsAdapter()

        searchAdapter.onItemClick = {
            val intent = Intent(requireContext(), MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }

        binding.recyclerSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }


    private fun setObserve() {
        viewModel.searchMealsLiveData.observe(requireActivity()) {
            searchAdapter.differ.submitList(it)
        }
    }
}