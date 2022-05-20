package com.fit.iugaza.edu.ps.qra.std.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.std.databinding.ActivityStatisticsBinding

class Statistics : AppCompatActivity() {
    var _binding: ActivityStatisticsBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants().statusBarColor(this)
        binding.apply {
            appbar.title.text = "الإحصائيات"
            subTitle.text = intent.getStringExtra("title")
            appbar.btnBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}