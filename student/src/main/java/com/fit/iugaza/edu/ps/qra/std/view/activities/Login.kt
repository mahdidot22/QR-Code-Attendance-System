package com.fit.iugaza.edu.ps.qra.std.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.std.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnLogin.setOnClickListener {
                Constants().navigation(
                    this@Login,
                    Container::class.java
                )
            }
        }
    }
}