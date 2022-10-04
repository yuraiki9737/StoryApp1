package com.navigation.latihan.storyappsubmission1intermediateandroid.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.navigation.latihan.storyappsubmission1intermediateandroid.data.response.LoginStory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceAkunStory  private constructor(private val dataStoreStoryApp: DataStore<Preferences>) {

    fun getAkunStory(): Flow<LoginStory>{
        return dataStoreStoryApp.data.map { preferences ->
            LoginStory(
                preferences[ACCOUNT_ID_KEY]?:"",
                preferences[ACCOUT_NAMA_KEY]?:"",
                preferences[ACCOUNT_TOKEN_KEY]?:"",
                preferences[ACCOUNT_STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveStoryApp(loginStory:LoginStory){
        dataStoreStoryApp.edit { preferences ->
            preferences[ACCOUNT_ID_KEY]= loginStory.userId
            preferences[ACCOUT_NAMA_KEY]= loginStory.name
            preferences[ACCOUNT_TOKEN_KEY]= loginStory.token
            preferences[ACCOUNT_STATE_KEY]= loginStory.isLogin

        }
    }

    suspend fun login(){
        dataStoreStoryApp.edit { preferences ->
            preferences[ACCOUNT_STATE_KEY]= true
        }
    }

    suspend fun logoutStoryApp(){
        dataStoreStoryApp.edit { preferences ->
            preferences[ACCOUNT_STATE_KEY] = false
        }
    }

    companion object{
        @Volatile
        private var INSTANCESTORYAPP : PreferenceAkunStory? = null

        private val ACCOUNT_ID_KEY = stringPreferencesKey("userid")
        private val ACCOUT_NAMA_KEY = stringPreferencesKey("name")
        private val ACCOUNT_TOKEN_KEY = stringPreferencesKey("token")
        private val ACCOUNT_STATE_KEY = booleanPreferencesKey("state")

        fun getInstanceStoryApp(dataStoreStoryApp: DataStore<Preferences>): PreferenceAkunStory {
            return INSTANCESTORYAPP ?: synchronized(this){
                val instanceStoryApp = PreferenceAkunStory(dataStoreStoryApp)
                INSTANCESTORYAPP = instanceStoryApp
                instanceStoryApp
            }
        }
    }

}