package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.FactoryViewModelStoryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage.PreferenceAkunStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivitySettingBinding
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.model.HomeViewModel
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.login.MainActivity
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting.preference.SettingAppStoryPrefrence


private val Context.dataStoreStoryApp: DataStore<Preferences> by preferencesDataStore(name = "akunstory")

class Setting : AppCompatActivity() {

    private val prefAppStory by lazy { SettingAppStoryPrefrence(this) }
    private lateinit var binding: ActivitySettingBinding
    private lateinit var modelSettingView : HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.themeMode.isChecked = prefAppStory.getBoolean(MODE_DARK_THEME)
        binding.themeMode.setOnCheckedChangeListener{ _: CompoundButton?, isChecked ->
            when(isChecked){
                true->{
                    prefAppStory.put(MODE_DARK_THEME,true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false ->{
                    prefAppStory.put(MODE_DARK_THEME,false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

            }

        }
        modelStoryView()
        bindingButton()

    }

    private fun bindingButton(){
        binding.btnFrame.setOnClickListener {
            binding.cardView.visibility = View.VISIBLE
        }

        binding.btnClose.setOnClickListener {
            binding.cardView.visibility = View.GONE
        }

        binding.btnLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }


        binding.btnLogout.setOnClickListener {
            binding.cardViewLogout.visibility = View.VISIBLE
        }

        binding.buttonYes.setOnClickListener{
            modelSettingView.logout()
            finish()
        }
        binding.buttonNo.setOnClickListener{
            binding.cardViewLogout.visibility=View.GONE
        }
    }


    private fun modelStoryView(){

        val preferences = PreferenceAkunStory.getInstanceStoryApp(dataStoreStoryApp)
        modelSettingView = ViewModelProvider(
            this, FactoryViewModelStoryApp(preferences)
        )[HomeViewModel::class.java]

        loadingSetting(true)
        modelSettingView.getUser().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this@Setting, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            loadingSetting(false)
            modelSettingView.setStoryApp(tokenAuthentication = user.token)
        }
    }

    private fun loadingSetting(state:Boolean){
        if (state){
            binding.progressLogout.visibility = View.VISIBLE
        }else{
            binding.progressLogout.visibility=View.GONE
        }
    }


    companion object{
        const val MODE_DARK_THEME = "mode_dark_theme"
    }
}
