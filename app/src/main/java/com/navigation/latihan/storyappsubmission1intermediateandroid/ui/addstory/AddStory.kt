package com.navigation.latihan.storyappsubmission1intermediateandroid.ui.addstory

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.FactoryViewModelStoryApp
import com.navigation.latihan.storyappsubmission1intermediateandroid.R
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.addstory.utils.reduceFileImage
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.addstory.utils.uriToFile
import com.navigation.latihan.storyappsubmission1intermediateandroid.api.RetrofitClient
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.ResponseRegisterStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage.PreferenceAkunStory
import com.navigation.latihan.storyappsubmission1intermediateandroid.databinding.ActivityAddStoryBinding
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.addstory.utils.rotateBitmapImage
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.home.model.HomeViewModel
import com.navigation.latihan.storyappsubmission1intermediateandroid.ui.setting.Setting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private val Context.dataStoreStoryApp: DataStore<Preferences> by preferencesDataStore(name = "akunStory")

class AddStory : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var addStoryModel : HomeViewModel

    companion object{
        const val Camera_X_RESULT_CODE = 200
        private val REQUIRED_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSION){
            if(!allPermissionGranted()){
                Toast.makeText(this, getString(R.string.permission_no_granted), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionGranted()){
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSION,
                REQUEST_CODE_PERMISSION
            )
        }
        bindingButton()
        viewModelAddStory()

    }

    private fun bindingButton(){
        binding.btnSetting.setOnClickListener {
            val view = Intent(this@AddStory, Setting::class.java)
            startActivity(view)

        }
        binding.buttonCamera.setOnClickListener { startCameraX() }
        binding.buttonGallery.setOnClickListener { addPhoto() }
        binding.upload.setOnClickListener { lifecycleScope.launch(Dispatchers.Main) { uploadImage() } }

    }

    private fun viewModelAddStory(){
        val preferences = PreferenceAkunStory.getInstanceStoryApp(dataStoreStoryApp)
        addStoryModel = ViewModelProvider(this, FactoryViewModelStoryApp(preferences))[HomeViewModel::class.java]
    }

    private fun addPhoto(){
        val intentPhoto = Intent()
            intentPhoto.action = Intent.ACTION_GET_CONTENT
            intentPhoto.type = "image/*"
        val chooserPhoto = Intent.createChooser(intentPhoto,"Choose a Picture")
        launcherGallery.launch(chooserPhoto)
    }

    private var getFile: File? = null
    private val launcherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == RESULT_OK){
            val selectedImage : Uri = result.data?.data as Uri
            val fileImage = uriToFile(selectedImage, this@AddStory)
            getFile = fileImage

            binding.photo.setImageURI(selectedImage)
        }
    }

    private fun startCameraX(){
        val intentCamera = Intent(this, CameraX::class.java)
        launcherCamera.launch(intentCamera)
    }

    private val launcherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Camera_X_RESULT_CODE){
            val fileCamera = it.data?.getSerializableExtra("picture") as File
            val isBackCamera= it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = fileCamera
            val result = rotateBitmapImage( BitmapFactory.decodeFile(getFile?.path), isBackCamera)
            binding.photo.setImageBitmap(result)
        }
    }

    private fun uploadImage(){
        if (getFile != null){
            val fileImage = reduceFileImage(getFile as File)
            val descriptionText = binding.descriptionEdit.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImage = fileImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multipartImage : MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                fileImage.name,
                requestImage
            )
            binding.upload.visibility = View.GONE

            binding.progressAddStory.visibility = View.VISIBLE

            addStoryModel.getUser().observe(this){
                user ->

                val service = RetrofitClient().getApiStoryApp().imageUploadStory(multipartImage, descriptionText, "Bearer ${user.token}")
                service.enqueue(object : Callback<ResponseRegisterStory>{
                    override fun onResponse(
                        call: Call<ResponseRegisterStory>,
                        response: Response<ResponseRegisterStory>,
                    ) {
                        if (response.isSuccessful){
                            val responBody = response.body()
                            if (responBody != null && !responBody.error) {
                                Toast.makeText(this@AddStory,
                                    getString(R.string.success_upload),
                                    Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        } else {
                            Toast.makeText(this@AddStory, response.message(), Toast.LENGTH_SHORT).show()
                            binding.upload.visibility = View.VISIBLE
                        }
                    }

                    override fun onFailure(call: Call<ResponseRegisterStory>, t: Throwable) {
                        Toast.makeText(this@AddStory, getString(R.string.failed_retrofit), Toast.LENGTH_SHORT).show()
                        binding.upload.visibility = View.VISIBLE
                    }

                })
            }
        } else {
            Toast.makeText(this@AddStory, getString(R.string.add_warning_image), Toast.LENGTH_SHORT).show()

        }
    }
}