package com.navigation.latihan.storyappsubmission1intermediateandroid.data.response

data class ResponseLoginStory (

    val error: Boolean,
    val message: String,
    val loginResult: LoginStory
        )

data class LoginStory(

    val userId: String,
    val name: String,
    val token: String,
    val isLogin: Boolean,

    )