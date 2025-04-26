package com.android.asatabai.data

import android.app.Application
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoute

class AppData : Application() {
    var isNightMode: Boolean = false
    val recentRoutes: MutableList<JeepneyRoute> = mutableListOf()
    var username: String = ""
    var email: String = ""
}