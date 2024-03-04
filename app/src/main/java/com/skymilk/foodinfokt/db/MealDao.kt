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
    suspend fun upsert(meal: Meal): Long // 삽입 행 수 결과 리턴

    @Delete
    suspend fun delete(meal: Meal): Int // 삭제 행 수 결과 리턴

    @Query("SELECT * FROM mealInformation")
    fun getAllFavoriteMeals(): LiveData<List<Meal>>


    @Query("SELECT EXISTS(SELECT * FROM mealInformation Where idMeal = :id)")
    suspend fun getIsFavoriteMeal(id: String): Boolean
}