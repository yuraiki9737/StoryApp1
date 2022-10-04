package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.login.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.LoginStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage.PreferenceAkunStory
import kotlinx.coroutines.launch


class MainViewModel (private val preferenceAkunStory: PreferenceAkunStory) : ViewModel() {

    fun saveStoryApp(loginStory: LoginStory){
        viewModelScope.launch {
            preferenceAkunStory.saveStoryApp(LoginStory(loginStory.userId, loginStory.name, loginStory.token,loginStory.isLogin))
        }
    }


    fun getAkunStory(): LiveData<LoginStory>{
        return preferenceAkunStory.getAkunStory().asLiveData()

    }

}