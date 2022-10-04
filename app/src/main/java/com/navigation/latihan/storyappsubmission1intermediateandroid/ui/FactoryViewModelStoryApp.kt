package com.navigation.latihan.storyappsubmission1intermediateandroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage.PreferenceAkunStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.model.HomeViewModel
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.login.model.MainViewModel

class FactoryViewModelStoryApp (private val preferenceStoryApp: PreferenceAkunStory) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(preferenceStoryApp) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(preferenceStoryApp) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
