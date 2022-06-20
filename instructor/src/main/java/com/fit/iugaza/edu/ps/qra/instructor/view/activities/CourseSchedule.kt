package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityCourseScheduleBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.course_appointment
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.CoursesAppointmentsAdapter

class CourseSchedule : AppCompatActivity() {
    private var _binding: ActivityCourseScheduleBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCourseScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants().statusBarColor(this)
        val course = arrayListOf<course_appointment>()
        course.add(course_appointment("السبت", "10:00-11:30", "I117"))
        course.add(course_appointment("الثلاثاء", "10:00-11:30", "I117"))
        course.add(course_appointment("الخميس", "10:00-11:30", "I117"))
        binding.apply {
            appbar.title.text = intent.getStringExtra("title")
            appbar.btnBack.setOnClickListener {
                finish()
            }
            recyclerView.adapter = CoursesAppointmentsAdapter(this@CourseSchedule, course)
            recyclerView.layoutManager = LinearLayoutManager(this@CourseSchedule)

            btnDivisions.setOnClickListener {
                Constants().navigation(this@CourseSchedule, CourseDivisions::class.java)
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}