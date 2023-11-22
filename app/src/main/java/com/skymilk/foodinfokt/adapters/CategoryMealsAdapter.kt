package com.skymilk.foodinfokt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.databinding.MealItemBinding
import com.skymilk.foodinfokt.models.MealsByCategory

class CategoryMealsAdapter() :
    RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    private var mealsByCategoryList = ArrayList<MealsByCategory>()

    fun setMealList(mealsByCategoryList: ArrayList<MealsByCategory>) {
        this.mealsByCategoryList = mealsByCategoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsByCategoryList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val mealsByCategory = mealsByCategoryList[position]

        //이미지 적용
        Glide.with(holder.itemView).load(mealsByCategory.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.txtMealName.text = mealsByCategory.strMeal

        //아이템 터치 리스터
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsByCategory)
        }
    }

    inner class CategoryMealsViewHolder(var binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}