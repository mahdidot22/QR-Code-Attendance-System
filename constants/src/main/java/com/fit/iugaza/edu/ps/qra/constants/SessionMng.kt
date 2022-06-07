package com.fit.iugaza.edu.ps.qra.constants

import android.content.Context
import android.content.SharedPreferences

class SessionMng(context: Context){
    var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }

    fun setId(id: String, key: String) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(key, id)
        editor.apply()
    }

    fun getId(key:String):String? {
        return sharedPreferences!!.getString(key, "")
    }

    fun logout(){
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }
}