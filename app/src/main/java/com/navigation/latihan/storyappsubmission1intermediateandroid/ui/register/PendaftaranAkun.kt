package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.navigation.latihan.storyappsubmission1intermediateandroid.R
import com.navigation.latihan.storyappsubmission1intermediateandroid.api.RetrofitClient
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.RegisterUser
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivityPendaftaranAkunBinding
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.ResponseRegisterStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.login.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PendaftaranAkun : AppCompatActivity() {
    private lateinit var binding: ActivityPendaftaranAkunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingConfigure()


    }

    private fun bindingConfigure() {

        binding.buttonRegistrationAccount.setOnClickListener {

            val emailAccountStoryApp = binding.username.text.toString()
            val nameAccountStoryApp = binding.nameEdit.text.toString()
            val passwordAccount = binding.password.text.toString()


            when {

                nameAccountStoryApp.isEmpty() -> {
                    binding.nameEdit.error = getString(R.string.name_account)
                }
                emailAccountStoryApp.isEmpty() -> {
                    binding.username.error = getString(R.string.email_account)
                }
                passwordAccount.isEmpty() -> {
                    binding.password.error = getString(R.string.password_account)
                }
            else ->{

                registrationAccountStoryApp()


                }

            }

        }


    }


    private fun registrationAccountStoryApp() {
        val emailAccountStoryApp = binding.username.text.toString().trim()
        val nameAccountStoryApp = binding.nameEdit.text.toString().trim()
        val passwordAccount = binding.password.text.toString().trim()

                binding.progressRegister.visibility = View.VISIBLE

                RetrofitClient().getApiStoryApp().registerAkunStoryApp(RegisterUser(
                    nameAccountStoryApp,
                    emailAccountStoryApp,
                    passwordAccount))
                    .enqueue(object : Callback<ResponseRegisterStory> {
                        override fun onFailure(call: Call<ResponseRegisterStory>, t: Throwable) {
                            binding.progressRegister.visibility = View.INVISIBLE
                            Log.d("failure: ", t.message.toString())
                        }

                        override fun onResponse(
                            call: Call<ResponseRegisterStory>,
                            response: Response<ResponseRegisterStory>,
                        ) {
                            if (response.isSuccessful) {
                                binding.progressRegister.visibility = View.VISIBLE
                                Toast.makeText(applicationContext,
                                    getString(R.string.createdAccount),
                                    Toast.LENGTH_LONG).show()
                                val registrationIntentAccount = Intent(this@PendaftaranAkun, MainActivity::class.java)
                                startActivity(registrationIntentAccount)
                                finish()

                            } else {
                                binding.progressRegister.visibility = View.INVISIBLE
                                Toast.makeText(applicationContext,
                                    getString(R.string.invalidAccount),
                                    Toast.LENGTH_SHORT).show()
                            }


                        }

                    })
            }


}



