package com.skymilk.foodinfokt.di

import android.app.Application
import androidx.room.Room
import com.skymilk.foodinfokt.db.MealDao
import com.skymilk.foodinfokt.db.MealDatabase
import com.skymilk.foodinfokt.db.MealTypeConvertor
import com.skymilk.foodinfokt.repository.MealRepository
import com.skymilk.foodinfokt.repository.MealRepositoryImpl
import com.skymilk.foodinfokt.retrofit.MealApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //MealApi 의존성 주입
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideMealApi(): MealApi {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    //Room Database 의존성 주입
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideMealDatabase(
        application: Application
    ): MealDatabase {
        return Room.databaseBuilder(
            application,
            MealDatabase::class.java,
            "meal.db"
        )
            .addTypeConverter(MealTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDao(
        mealDatabase: MealDatabase
    ): MealDao = mealDatabase.mealDao
    ///////////////////////////////////////////////////////////////////////////////////////////////////////



    //Repository 의존성 주입
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideMealRepository(
        mealApi: MealApi,
        mealDao: MealDao
    ) : MealRepository = MealRepositoryImpl(mealApi, mealDao)
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
}