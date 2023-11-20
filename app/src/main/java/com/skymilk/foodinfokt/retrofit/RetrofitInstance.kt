package com.skymilk.foodinfokt.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: MealApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") // http or https 키워드가 필수로 들어가야 한다
            .addConverterFactory(GsonConverterFactory.create()) // GSON 컨버터 설정
            .build()
            .create(MealApi::class.java)
    }
}