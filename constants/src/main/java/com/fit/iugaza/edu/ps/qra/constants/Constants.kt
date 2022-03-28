package com.fit.iugaza.edu.ps.qra.constants

import java.util.*
import kotlin.concurrent.schedule

class Constants {
    fun loadSplashScreen(toDo: () -> Unit) {
        Timer().schedule(2000) {
            toDo.invoke()
        }
    }
}