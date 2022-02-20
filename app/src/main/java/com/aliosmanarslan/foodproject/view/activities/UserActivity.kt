package com.aliosmanarslan.foodproject.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.aliosmanarslan.foodproject.R
import com.aliosmanarslan.foodproject.viewmodel.UserActivityViewModel

class UserActivity : AppCompatActivity() {

    private lateinit var viewModel : UserActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FoodProject)
        setContentView(R.layout.activity_user)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val tempViewModel : UserActivityViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onStart() {
        super.onStart()
        viewModel.currentUserControl(this, this)
    }
}