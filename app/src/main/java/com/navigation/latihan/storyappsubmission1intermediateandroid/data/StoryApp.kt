package com.navigation.latihan.storyappsubmission1intermediateandroid.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryApp(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String
) : Parcelable