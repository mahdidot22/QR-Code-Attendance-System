package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityCourseDevisionsBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.divisions
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.DivisionsAdapter

class CourseDivisions : AppCompatActivity() {
    private var _binding: ActivityCourseDevisionsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Constants().statusBarColor(this)
        _binding = ActivityCourseDevisionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            appbar.title.text = "عرض الشعب"
            appbar.btnBack.setOnClickListener { finish() }
            val divisions = arrayListOf<divisions>()
            divisions.add(divisions("برمجة الألعاب النظري","101"))
            divisions.add(divisions(" برمجة الويب النظري","101"))
            divisions.add(divisions("برمجة الألعاب العملي","102"))
            divisions.add(divisions("برمجة الويب العملي","102"))
            divisions.add(divisions("برمجة الموبايل 1","101"))
            divisions.add(divisions("برمجة الموبايل 1 العملي","102"))
            divisionsList.adapter = DivisionsAdapter(this@CourseDivisions,divisions)
            divisionsList.layoutManager = LinearLayoutManager(this@CourseDivisions)
        }
    }
}