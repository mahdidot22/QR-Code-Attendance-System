package com.fit.iugaza.edu.ps.qra.std.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.iugaza.edu.ps.qra.std.databinding.ActivityContainerBinding

class Container : AppCompatActivity() {
    private var _binding: ActivityContainerBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}