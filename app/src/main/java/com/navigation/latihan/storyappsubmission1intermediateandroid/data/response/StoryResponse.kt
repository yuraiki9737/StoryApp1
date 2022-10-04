package com.navigation.latihan.storyappsubmission1intermediateandroid.data.response

import com.navigation.latihan.storyappsubmission1intermediateandroid.data.StoryApp

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: ArrayList<StoryApp>
)