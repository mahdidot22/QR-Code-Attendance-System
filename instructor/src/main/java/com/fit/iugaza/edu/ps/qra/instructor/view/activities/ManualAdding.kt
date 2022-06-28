package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityManualAddingBinding
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.ManualAddingAdapter

class ManualAdding : AppCompatActivity() {
    private var _binding: ActivityManualAddingBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityManualAddingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants().statusBarColor(this)
        binding.apply {
            appbar.title.text = "إضافة طلاب"
            appbar.btnBack.setOnClickListener { finish() }
            items.adapter = ManualAddingAdapter(this@ManualAdding,intent.getStringExtra("courseId").toString())
            items.layoutManager = LinearLayoutManager(this@ManualAdding)
        }

    }
}