package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivitySplashScreenStroryAppBinding
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.Home

class SplashScreenStroryApp : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenStroryAppBinding
    private val second = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenStroryAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, Home::class.java))
            finish()
        }, second)
    }
}