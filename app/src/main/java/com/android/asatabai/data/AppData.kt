package com.android.asatabai.data

import android.app.Application
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoute

class AppData : Application() {
    var isNightMode: Boolean = false
    var username: String = ""
    var email: String = ""

}