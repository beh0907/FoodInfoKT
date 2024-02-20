package com.skymilk.foodinfokt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.skymilk.foodinfokt.activities.MainActivity
import com.skymilk.foodinfokt.adapters.MealsAdapter
import com.skymilk.foodinfokt.databinding.FragmentFavoritesBinding
import com.skymilk.foodinfokt.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val TAG = "FavoritesFragment"

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //프래그먼트 생성 시 뷰 모델 초기화
        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFavoritesRecyclerView()
        setObserve()
    }

    private fun initFavoritesRecyclerView() {
        mealsAdapter = MealsAdapter()

        mealsAdapter.onItemClick = {

        }

        binding.recyclerFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }

        //아이템 터치 이벤트 동작
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, //드래그 방향
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT // 스와이프 방향
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            //즐겨찾기 목록 아이템을 스와이프시 삭제한다
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = mealsAdapter.differ.currentList[position]

                viewModel.deleteFavoriteMeal(meal)

                Snackbar.make(requireView(), "삭제되었습니다", Snackbar.LENGTH_LONG).setAction(
                    "취소",
                    View.OnClickListener {
                        //취소할 경우 삭제했던 음식 정보를 다시 추가한다.
                        viewModel.insertFavoriteMeal(meal)
                    }
                ).show()
            }
        }

        //생성한 이벤트를 리사이클러뷰에 적용
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerFavorites)
    }

    private fun setObserve() {
        //activity!!를 쓸 경우 NullPointerException가 발생할 수 있다
        //requireActivity()는 activity가 null일 때 IllegalStateException로 발생 시켜 좀 더 안전 하다
        viewModel.favoriteMealsLiveDatabase.observe(requireActivity()) {
            //새 리스트를 적용해 업데이트한다
            mealsAdapter.differ.submitList(it)
        }
    }
}