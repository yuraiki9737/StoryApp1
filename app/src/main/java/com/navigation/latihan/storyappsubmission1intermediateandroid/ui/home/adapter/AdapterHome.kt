package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.detailstory.DetailStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.StoryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.StoryAppItemBinding

class AdapterHome : RecyclerView.Adapter<AdapterHome.HomeViewHolder>() {

    private var listStoryUser : List<StoryApp>? = null

    fun setStoryListUser (listStoryUser: List<StoryApp>?){
        this.listStoryUser = listStoryUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val viewStory = StoryAppItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return HomeViewHolder(viewStory)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listStoryUser?.get(position)!!)
    }

    override fun getItemCount(): Int {
      return if(listStoryUser ==  null)0
        else listStoryUser?.size!!
    }
    inner class HomeViewHolder(private val binding: StoryAppItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataStory : StoryApp){
            itemView.setOnClickListener {
                val intentStory = Intent(itemView.context, DetailStory::class.java)
                intentStory.putExtra("storyData", dataStory)


                val optionsCompat : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,

                    Pair(binding.photo, "image"),
                    Pair(binding.namePerson, "name"),
                    Pair(binding.description, "description")
                )

                itemView.context.startActivity(intentStory, optionsCompat.toBundle())
            }

            binding.apply {
                Glide.with(itemView)
                    .load(dataStory.photoUrl)
                    .centerCrop()
                    .into(photo)

                namePerson.text = dataStory.name
                description.text = dataStory.description

            }
        }


    }

}