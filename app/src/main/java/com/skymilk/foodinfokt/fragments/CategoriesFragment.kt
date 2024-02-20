package com.skymilk.foodinfokt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.skymilk.foodinfokt.activities.MainActivity
import com.skymilk.foodinfokt.adapters.CategoriesAdapter
import com.skymilk.foodinfokt.databinding.FragmentCategoriesBinding
import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //프래그먼트 생성 시 뷰 모델 초기화
        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCategoriesRecyclerView()

        setObserve()
    }

    private fun initCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()

        categoriesAdapter.onItemClick = {

        }

        binding.recyclerCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun setObserve() {
        viewModel.categoriesLiveData.observe(requireActivity()) {
            categoriesAdapter.setCategoryList(it as ArrayList<Category>)
        }
    }
}