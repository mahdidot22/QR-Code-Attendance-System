package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityCourseScheduleBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.course_appointment
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.CoursesAppointmentsAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CourseSchedule : AppCompatActivity() {
    private var _binding: ActivityCourseScheduleBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCourseScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants().statusBarColor(this)
        val db = Firebase.firestore
        binding.apply {
            appbar.title.text = intent.getStringExtra("courseName")
            appbar.btnBack.setOnClickListener {
                finish()
            }
            getAppointment(db)
            btnDivisions.setOnClickListener {
                Constants().navigation(intent.getStringExtra("courseId").toString(),this@CourseSchedule, CourseDivisions::class.java)
            }
        }

    }
    fun getAppointment(db: FirebaseFirestore) {
        db.collection("QRAcourses").whereEqualTo("courseId", intent.getStringExtra("courseId"))
            .get().addOnSuccessListener { result ->
                for (doc in result) {
                    val course_appointments =
                        doc.get("courseAppointment") as ArrayList<Map<String, String>>
                    val list = arrayListOf<course_appointment>()
                    for (c in course_appointments) {
                        list.add(
                            course_appointment(
                                c["day"]!!,
                                c["startTime"]!!,
                                c["startMinute"]!!,
                                c["endTime"]!!,
                                c["room"]!!,
                                intent.getStringExtra("courseId")!!
                            )
                        )
                        binding.recyclerView.adapter =
                            CoursesAppointmentsAdapter(this@CourseSchedule, list)
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(this@CourseSchedule)
                    }
                }
            }.addOnFailureListener { mag ->
                mag.localizedMessage?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}