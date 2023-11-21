package com.skymilk.foodinfokt.retrofit

import com.skymilk.foodinfokt.models.CategoryList
import com.skymilk.foodinfokt.models.MealByCategoryList
import com.skymilk.foodinfokt.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    //무작위 조회 1개
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    //상세 정보 조회
    @GET("lookup.php?")
    fun getDetailMeal(@Query("i") id:String): Call<MealList>

    //조건 조회
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String): Call<MealByCategoryList>

    //카테고리 목록 조회
    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

}