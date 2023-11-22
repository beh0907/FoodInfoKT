package com.skymilk.foodinfokt.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skymilk.foodinfokt.models.Meal

@Dao //Room DAO 지정 어노테이션
interface MealDao {
    //ABORT = 중단
    //FAIL = 실패
    //IGNORE = 무시
    //REPLACE = 덮어쓰기
    //ROLLBACK = 이전으로 되돌리기
    @Insert(onConflict = OnConflictStrategy.REPLACE) //이미 데이터가 있다면 덮어쓰므로 update 동작을 한다
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>
}