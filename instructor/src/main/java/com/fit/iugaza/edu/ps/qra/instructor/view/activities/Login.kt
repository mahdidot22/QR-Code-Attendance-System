package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityLoginBinding


class Login : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userDemo: String = "120192424"
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnLogin.setOnClickListener {
                if (edStdId.text.isNullOrEmpty()) {
                    Toast.makeText(this@Login, "fill fields", Toast.LENGTH_SHORT).show()
                } else {
                    if (edStdId.text.toString() == userDemo) {
                        SessionMng(this@Login).setId(userDemo, "id")
                        Constants().navigation(
                            this@Login,
                            Container::class.java
                        )
                        finish()
                    } else {
                        Toast.makeText(
                            this@Login,
                            "Invalid username or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!SessionMng(this).getId("id").isNullOrEmpty()) {
            Constants().navigation(this, Container::class.java)
        }
    }
}