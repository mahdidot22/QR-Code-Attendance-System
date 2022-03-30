package com.fit.iugaza.edu.ps.qra.std

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.std.databinding.SplashMainActivityBinding
import com.fit.iugaza.edu.ps.qra.std.view.Login

class MainActivity : AppCompatActivity() {
    lateinit var constants: Constants
    private var _binding: SplashMainActivityBinding? = null
    private val binding get() = _binding!!

    //TODO test login session here
    override fun onStart() {
        super.onStart()
    }

    //TODO fetch data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SplashMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        constants = Constants()
        constants.loadSplashScreen {
            runOnUiThread {
                constants.navigation(source = this, destination = Login::class.java)
                finish()
            }
        }
    }
}