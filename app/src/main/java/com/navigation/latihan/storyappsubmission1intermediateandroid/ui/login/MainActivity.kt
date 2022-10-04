package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.FactoryViewModelStoryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.Home
import com.navigation.latihan.storyappsubmission1intermediateandroid.R
import com.navigation.latihan.storyappsubmission1intermediateandroid.api.RetrofitClient
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.LoginUser
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.LoginStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivityMainBinding
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.register.PendaftaranAkun
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.ResponseLoginStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage.PreferenceAkunStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.login.model.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStoreStoryApp: DataStore<Preferences> by preferencesDataStore(name = "akunStory")

class MainActivity : AppCompatActivity() {


    private  lateinit var binding : ActivityMainBinding
    private lateinit var loginStoryModel : MainViewModel
    private lateinit var login : LoginStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupModelViewStory()
        bindingButton()
        loginAccount()
    }

    private fun bindingButton(){
        binding.registrationAccount.setOnClickListener {
            val view = Intent(this@MainActivity, PendaftaranAkun::class.java)
            startActivity(view)

        }

    }

    private fun setupModelViewStory(){
        val preference= PreferenceAkunStory.getInstanceStoryApp(dataStoreStoryApp)
        loginStoryModel = ViewModelProvider(
            this@MainActivity,
            FactoryViewModelStoryApp(preference)
        )[MainViewModel::class.java]

        loginStoryModel.getAkunStory().observe(this){login->
            this.login = login
        }
    }

    private fun loginAccount(){
        binding.buttonLogin.setOnClickListener {
            val emailAkunStory = binding.username.text.toString()
            val passwordAkunStory = binding.password.text.toString().trim()

            binding.progressLogin.visibility = View.VISIBLE
            RetrofitClient().getApiStoryApp()
                .loginAkunStoryApp(LoginUser(emailAkunStory, passwordAkunStory))
                .enqueue(object : Callback<ResponseLoginStory> {

                    override fun onResponse(
                        call: Call<ResponseLoginStory>,
                        response: Response<ResponseLoginStory>,
                    ) {
                        if (response.code() == 200) {
                            val bodyUser = response.body()?.loginResult as LoginStory

                            loginStoryModel.saveStoryApp(LoginStory(bodyUser.userId,
                                bodyUser.name,
                                bodyUser.token,
                                true))
                            binding.progressLogin.visibility = View.INVISIBLE

                            Toast.makeText(applicationContext,
                                getString((R.string.sukses)),
                                Toast.LENGTH_SHORT).show()
                            val intentStory = Intent(this@MainActivity, Home::class.java)
                            intentStory.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intentStory)
                            finish()
                        } else {

                            binding.progressLogin.visibility = View.INVISIBLE

                            Toast.makeText(applicationContext,
                                getString(R.string.failed),
                                Toast.LENGTH_SHORT).show()
                            Log.d(MainActivity::class.java.simpleName,
                                response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseLoginStory>, t: Throwable) {
                        Log.d("failure: ", t.message.toString())
                    }


                })
        }
    }



}