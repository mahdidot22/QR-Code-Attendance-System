package com.fit.iugaza.edu.ps.qra.constants

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    fun loadToast(toDo: () -> Unit) {
        Timer().schedule(3000) {
            toDo.invoke()
        }
    }
    fun createToast(activity: AppCompatActivity,toastText:TextView,msg:View, text:String){
        msg.visibility = View.VISIBLE
        toastText.text = text
        Constants().loadToast {
            activity.runOnUiThread {
                msg.visibility = View.GONE
            }
        }
    }

    fun navigation(source: Context, destination: Class<*>) {
        source.startActivity(Intent(source, destination))
    }

    fun navigation(source: Context, destination: Class<*>,divStudents:ArrayList<String>,courseId: String) {
        source.startActivity(Intent(source, destination).putExtra("students",divStudents).putExtra("courseId",courseId))
    }


    fun navigation(source: Context, destination: Class<*>, title: String) {
        source.startActivity(Intent(source, destination).putExtra("title", title))
    }
    fun navigation( courseId: String, source: Context, destination: Class<*>) {
        source.startActivity(Intent(source, destination).putExtra("courseId", courseId))
    }

    fun navigation(source: Context, destination: Class<*>, courseName: String, courseId: String) {
        source.startActivity(
            Intent(source, destination).putExtra("courseName", courseName).putExtra("courseId", courseId)
        )
    }

    fun navigation( startTime: String,startMinute: String,courseId: String, source: Context, destination: Class<*>) {
        source.startActivity(
            Intent(source, destination).putExtra("startTime", startTime).putExtra("startMinute",startMinute).putExtra("courseId",courseId)
        )
    }

    fun navigation(source: Context, destination: Class<*>, courseId: String, divisionId:String, courseName: String) {
        source.startActivity(
            Intent(source, destination).putExtra("divisionId", divisionId).putExtra("courseName",courseName).putExtra("courseId",courseId)
        )
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

    fun showDialog(
        context: Context, mainIcon: Int, title: String, msg: String,
        positiveBtnText: String, negativeBtnText: String?,
        positiveBtnClickListener: DialogInterface.OnClickListener,
        negativeBtnClickListener: DialogInterface.OnClickListener?
    ): androidx.appcompat.app.AlertDialog {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle(title)
            .setIcon(mainIcon)
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton(positiveBtnText, positiveBtnClickListener)
        if (negativeBtnText != null)
            builder.setNegativeButton(negativeBtnText, negativeBtnClickListener)
        val alert = builder.create()
        alert.show()
        return alert
    }

    fun darkNight(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    @SuppressLint("HardwareIds")
    fun deviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

}