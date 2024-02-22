package com.skymilk.foodinfokt.retrofit

import com.skymilk.foodinfokt.models.Category
import com.skymilk.foodinfokt.models.Meal
import com.skymilk.foodinfokt.models.MealsByCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    //무작위 조회 1개
    @GET("random.php")
    fun getRandomMeal(): Call<List<Meal>>

    //상세 정보 조회
    @GET("lookup.php")
    fun getDetailMeal(@Query("i") id:String): Call<List<Meal>>

    //인기 음식 목록 조회
    //API 유료 문제로 임시로 필터링 API 활용
    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String): Call<List<MealsByCategory>>

    //카테고리 목록 조회
    @GET("categories.php")
    fun getCategories(): Call<List<Category>>

    //조건 조회
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String): Call<List<MealsByCategory>>

    //음식 이름 검색
    @GET("search.php")
    fun searchMealsByKeyword(@Query("s") keyword:String): Call<List<Meal>>
}