package com.fit.iugaza.edu.ps.qra.constants

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    fun mainBottomNavigation(
        bottomNavigation: BottomNavigationView,
        courses: Fragment,
        settings: Fragment,
        profile: Fragment,
        source: AppCompatActivity,
        root: Int,
        title: TextView
    ) {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.courses -> {
                    title.text = "جدول المساقات"
                    source.supportFragmentManager.beginTransaction().replace(root, courses).commit()
                    true
                }
                R.id.settings -> {
                    title.text = "الإعدادات"
                    source.supportFragmentManager.beginTransaction().replace(root, settings)
                        .commit()
                    true
                }
                R.id.profile -> {
                    title.text = "الملف الشخصي"
                    source.supportFragmentManager.beginTransaction().replace(root, profile).commit()
                    true
                }
                else -> false
            }
        }
    }

    fun orientationTransaction(
        activity: AppCompatActivity,
        fragmentKey: Int,
        root: Int,
        Courses: Fragment,
        Settings: Fragment,
        Profile: Fragment
    ) {
        when (fragmentKey) {
            R.id.courses -> {
                activity.supportFragmentManager.beginTransaction().replace(root, Courses)
            }
            R.id.settings -> {
                activity.supportFragmentManager.beginTransaction()
                    .replace(root, Settings)
            }
            R.id.profile -> {
                activity.supportFragmentManager.beginTransaction()
                    .replace(root, Profile)
            }
        }
    }

    fun statusBarColor(activity: Activity) {
        val window: Window = activity.window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.barColor)
    }
}