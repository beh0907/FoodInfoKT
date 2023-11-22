package com.skymilk.foodinfokt.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skymilk.foodinfokt.R
import com.skymilk.foodinfokt.db.MealDatabase
import com.skymilk.foodinfokt.viewModels.HomeViewModel
import com.skymilk.foodinfokt.viewModels.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    //프래그먼트 Home View Model
    val homeViewModel: HomeViewModel by viewModels { // 팩토리 커스텀을 했을 경우 처리
        val mealDatabase = MealDatabase.getInstance(this)
        HomeViewModelFactory(mealDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = Navigation.findNavController(this, R.id.hostFragment)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}