package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting.preference

import android.content.Context
import android.content.SharedPreferences

class SettingAppStoryPrefrence (prefContext: Context) {

    private val nameAppStoryMe = "AppStoryMe"
    private var prefAppStoryMe: SharedPreferences =
        prefContext.getSharedPreferences(nameAppStoryMe, Context.MODE_PRIVATE)
    private val prefEditAppStoryMe: SharedPreferences.Editor = prefAppStoryMe.edit()

    fun put(key: String, value: Boolean) {
        prefEditAppStoryMe.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return prefAppStoryMe.getBoolean(key, false)
    }
}