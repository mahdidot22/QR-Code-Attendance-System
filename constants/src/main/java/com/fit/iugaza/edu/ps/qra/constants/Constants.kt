package com.fit.iugaza.edu.ps.qra.constants

import android.content.Context
import android.content.Intent
import java.util.*
import kotlin.concurrent.schedule

class Constants {
    fun loadSplashScreen(toDo: () -> Unit) {
        Timer().schedule(2000) {
            toDo.invoke()
        }
    }

    fun navigation(source: Context, destination: Class<*>) {
        source.startActivity(Intent(source, destination))
    }
}