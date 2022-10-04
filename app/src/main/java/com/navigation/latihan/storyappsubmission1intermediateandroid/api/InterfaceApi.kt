package com.navigation.latihan.storyappsubmission1intermediateandroid.api

import com.navigation.latihan.storyappsubmission1intermediateandroid.data.LoginUser
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.RegisterUser
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.ResponseLoginStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.ResponseRegisterStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface InterfaceApi {

    @POST("login")
    fun loginAkunStoryApp(
        @Body login : LoginUser
    ):Call<ResponseLoginStory>

    @POST("register")
    fun registerAkunStoryApp(
        @Body register : RegisterUser
    ):Call<ResponseRegisterStory>


    @GET("stories")
    fun getStoryApp(@Header("Authorization") token: String): Call<StoryResponse>

    @Multipart
    @POST("/v1/stories")
    fun imageUploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String
    ): Call<ResponseRegisterStory>
}