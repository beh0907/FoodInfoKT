package com.skymilk.foodinfokt.retrofit

import com.skymilk.foodinfokt.models.CategoryList
import com.skymilk.foodinfokt.models.MealByCategoryList
import com.skymilk.foodinfokt.models.MealList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    //무작위 조회 1개
    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealList>

    //상세 정보 조회
    @GET("lookup.php")
    suspend fun getDetailMeal(@Query("i") id:String): Response<MealList>

    //인기 음식 목록 조회
    //API 유료 문제로 임시로 필터링 API 활용
    @GET("filter.php")
    suspend fun getPopularItems(@Query("c") categoryName:String): Response<MealByCategoryList>

    //카테고리 목록 조회
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryList>

    //조건 조회
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryName:String): Response<MealByCategoryList>

    //음식 이름 검색
    @GET("search.php")
    suspend fun searchMealsByKeyword(@Query("s") keyword:String): Response<MealList>
}