package com.fit.iugaza.edu.ps.qra.instructor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.SplashMainActivityBinding

class MainActivity : AppCompatActivity() {
    lateinit var constants: Constants
    private var _binding: SplashMainActivityBinding? = null
    private val binding get() = _binding!!

    //TODO test login session here
    override fun onStart() {
        super.onStart()
    }

    //TODO fetch data here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SplashMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        constants = Constants()
        constants.loadSplashScreen {
            runOnUiThread {
                Toast.makeText(applicationContext, "hi", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}