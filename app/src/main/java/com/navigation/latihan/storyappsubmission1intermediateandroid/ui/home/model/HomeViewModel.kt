package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.model

import android.util.Log
import androidx.lifecycle.*
import com.navigation.latihan.storyappsubmission1intermediateandroid.api.RetrofitClient
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.StoryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.LoginStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.StoryResponse
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage.PreferenceAkunStory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (private val preferenceAkunStory: PreferenceAkunStory) : ViewModel() {


    val storyAppList = MutableLiveData<ArrayList<StoryApp>?>()

    fun setStoryApp(tokenAuthentication : String){
        Log.d(this@HomeViewModel::class.java.simpleName, tokenAuthentication)
        RetrofitClient().getApiStoryApp().getStoryApp(token = "Bearer $tokenAuthentication")
            .enqueue(object : Callback<StoryResponse> {
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>
                ) {
                    if (response.isSuccessful) {
                        storyAppList.postValue(response.body()?.listStory)
                    } else {
                        storyAppList.postValue(null)
                    }
                }
                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    storyAppList.postValue(null)
                }

            })
    }

    fun getStoryApp(): MutableLiveData<ArrayList<StoryApp>?>{
        return storyAppList
    }

    fun getUser(): LiveData<LoginStory> {
        return preferenceAkunStory.getAkunStory().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            preferenceAkunStory.logoutStoryApp()
        }
    }
}