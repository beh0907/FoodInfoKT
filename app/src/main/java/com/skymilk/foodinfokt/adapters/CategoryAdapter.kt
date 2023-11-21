package com.skymilk.foodinfokt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.databinding.CategoryItemBinding
import com.skymilk.foodinfokt.models.Category

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    lateinit var onItemClick: ((Category) -> Unit)
    private var categoryList = ArrayList<Category>()

    fun setMealList(categoryList: ArrayList<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        //이미지 적용
        Glide.with(holder.itemView).load(category.strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.txtCategory.text = category.strCategory

        //아이템 터치 리스터
        holder.itemView.setOnClickListener {
            onItemClick.invoke(category)
        }
    }

    inner class CategoryViewHolder(var binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}