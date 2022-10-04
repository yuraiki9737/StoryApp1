package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.FactoryViewModelStoryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.addstory.AddStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage.PreferenceAkunStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting.Setting
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivityHomeBinding
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.adapter.AdapterHome
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.model.HomeViewModel
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.login.MainActivity

private val Context.dataStoreStoryApp: DataStore<Preferences> by preferencesDataStore(name = "akunStory")
class Home : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapterHome: AdapterHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingButton()
        recyclerViewInitStory()
        recyclerViewAllStory()


    }

    private fun bindingButton(){
        binding.btnCreate.setOnClickListener{
            val view = Intent(this@Home, AddStory::class.java)
            startActivity(view)
        }

        binding.btnSetting.setOnClickListener {
            val view = Intent(this@Home, Setting::class.java)
            startActivity(view)

        }
    }

    private fun recyclerViewInitStory(){
        binding.storyAppRv.layoutManager = LinearLayoutManager(this)
        adapterHome = AdapterHome()
        binding.storyAppRv.adapter = adapterHome
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerViewAllStory(){

        val preferencesAllStory = PreferenceAkunStory.getInstanceStoryApp(dataStoreStoryApp)
        homeViewModel = ViewModelProvider(
            this, FactoryViewModelStoryApp(preferencesAllStory)
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(this){story ->
            if(!story.isLogin){
                val intentStory = Intent(this@Home, MainActivity::class.java)
                intentStory.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentStory)
            }
            loadingHome(true)
            homeViewModel.setStoryApp(tokenAuthentication = story.token)
        }

        homeViewModel.getStoryApp().observe(this){
            if(it!=null){
                adapterHome.setStoryListUser(it)
                adapterHome.notifyDataSetChanged()
                loadingHome(false)

            }else{
                loadingHome(false)
            }
        }

    }

    private fun loadingHome(state:Boolean){
        if (state){
            binding.progressHome.visibility = View.VISIBLE
        }else{
            binding.progressHome.visibility= View.GONE
        }
    }
}