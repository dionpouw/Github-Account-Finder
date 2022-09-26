package com.aldion.githubuserv2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.aldion.githubuserv2.databinding.ActivityMainBinding
import com.aldion.githubuserv2.view.ui.listuseractivity.ListUserActivity
import com.aldion.githubuserv2.view.ui.settingactivity.SettingPreferences
import com.aldion.githubuserv2.view.ui.settingactivity.SettingViewModel
import com.aldion.githubuserv2.view.ui.settingactivity.SettingViewModelFactory
import kotlinx.coroutines.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)

        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        withCoroutine()
    }

    private fun withCoroutine() {
        val mScope = CoroutineScope(Dispatchers.Main)
        mScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                launchPostSplashActivity()
                mScope.cancel(null)
            }
        }
    }

    private fun launchPostSplashActivity() {
        // launch another activity
        val intent = Intent(this, ListUserActivity::class.java)
        startActivity(intent)
        finish() // necessary because we do not want user to come back to this activity
    }
}