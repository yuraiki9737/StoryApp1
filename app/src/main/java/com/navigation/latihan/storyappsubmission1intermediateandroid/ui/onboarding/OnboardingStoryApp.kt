package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting.Setting
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting.preference.SettingAppStoryPrefrence
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.splashscreen.SplashScreenStroryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivityOnboardingStoryAppBinding

class OnboardingStoryApp : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingStoryAppBinding
    private val prefAppStory by lazy { SettingAppStoryPrefrence(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingStoryAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val view = Intent(this@OnboardingStoryApp, SplashScreenStroryApp::class.java)
            startActivity(view)
		finish()
        }

        when (prefAppStory.getBoolean(Setting.MODE_DARK_THEME)) {
            true -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

    }




}
