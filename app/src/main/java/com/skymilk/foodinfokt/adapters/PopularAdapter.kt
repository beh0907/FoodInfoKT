package com.skymilk.foodinfokt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.databinding.PopularItemBinding
import com.skymilk.foodinfokt.models.MealsByCategory

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    lateinit var onItemLongClick: ((MealsByCategory) -> Unit)
    private var mealList = ArrayList<MealsByCategory>()

    fun setMealList(mealList: ArrayList<MealsByCategory>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            PopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val meal = mealList[position]

        //이미지 적용
        Glide.with(holder.itemView).load(meal.strMealThumb)
            .into(holder.binding.imgPopularMeal)
    }

    inner class PopularViewHolder(var binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            init {
                //아이템 터치 리스너
                itemView.setOnClickListener {
                    onItemClick.invoke(mealList[adapterPosition])
                }

                //아이템 롱 터치 리스너
                itemView.setOnLongClickListener {
                    onItemLongClick.invoke(mealList[adapterPosition])
                    true
                }
            }
    }
}