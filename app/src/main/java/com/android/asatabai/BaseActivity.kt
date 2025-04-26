package com.android.asatabai

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.android.asatabai.data.AppData
import com.android.asatabai.utils.LocaleHelper

open class BaseActivity : AppCompatActivity() {
    protected lateinit var sharedPreferences: SharedPreferences
    protected lateinit var editor: SharedPreferences.Editor
    protected var nightMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyNightMode()
    }

    protected fun applyNightMode() {

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    protected fun toggleNightMode(newState: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (newState) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        editor = sharedPreferences.edit()
        editor.putBoolean("night", newState)
        editor.apply()

        (application as AppData).isNightMode = newState
    }
}