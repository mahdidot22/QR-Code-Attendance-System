package com.fit.iugaza.edu.ps.qra.std

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.std.databinding.SplashMainActivityBinding
import com.fit.iugaza.edu.ps.qra.std.view.activities.Login

class MainActivity : AppCompatActivity() {
    lateinit var constants: Constants
    private var _binding: SplashMainActivityBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SplashMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        constants = Constants()
        Constants().statusBarColor(this)
        constants.loadSplashScreen {
            runOnUiThread {
                constants.navigation(source = this, destination = Login::class.java)
                finish()
            }
        }
    }
}