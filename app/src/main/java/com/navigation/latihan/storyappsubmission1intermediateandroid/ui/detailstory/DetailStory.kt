package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.detailstory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.addstory.AddStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.StoryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivityDetailStoryBinding
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting.Setting

class DetailStory : AppCompatActivity() {

    private lateinit var binding:ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingButton()
        viewDetailStory()
    }

    private fun bindingButton(){
        binding.btnCreate.setOnClickListener{
            val view = Intent(this@DetailStory, AddStory::class.java)
            startActivity(view)
        }

        binding.btnSetting.setOnClickListener {
            val view = Intent(this@DetailStory, Setting::class.java)
            startActivity(view)

        }
    }

    private fun viewDetailStory(){
        val detailStory = intent.getParcelableExtra<StoryApp>("storyData") as StoryApp
        binding.namePersonStory.text = detailStory.name
        binding.descriptionStory.text = detailStory.description

        Glide.with(this)
            .load(detailStory.photoUrl)
            .centerCrop()
            .into(binding.imageViewStory)
    }
}