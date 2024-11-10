package com.skymilk.foodinfokt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skymilk.foodinfokt.databinding.MealItemBinding
import com.skymilk.foodinfokt.models.Meal

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {

    //전체 재랜더링을 하는 notifyDataSetChanged의 성능 하락을 감안
    //diffUtil는 현재와 이전 상태를 비교해 최소한의 업데이트로 처리
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    lateinit var onItemClick: ((Meal) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        val meal = differ.currentList[position]

        //이미지 적용
        Glide.with(holder.itemView).load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.txtMealName.text = meal.strMeal
    }

    inner class MealsViewHolder(var binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //아이템 터치 리스너
            itemView.setOnClickListener {
                onItemClick.invoke(differ.currentList[adapterPosition])
            }
        }
    }
}