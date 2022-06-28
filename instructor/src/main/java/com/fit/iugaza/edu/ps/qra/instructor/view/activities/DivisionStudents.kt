package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.R
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityDivisionStudentsBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.students
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.DivStudentsAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DivisionStudents : AppCompatActivity() {
    private var _binding: ActivityDivisionStudentsBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDivisionStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants().statusBarColor(this)
        binding.apply {
            appbar.title.text = "الطلاب"
            appbar.btnBack.setOnClickListener { finish() }
        }
        getStudents()
    }
  private fun getStudents() {
        val courseId = intent.getStringExtra("courseId")
            val stds = arrayListOf<students>()
                db.collection("QRAcourses").whereEqualTo("courseId", courseId).get().addOnSuccessListener {
                    it.forEach {course->
                        val regStudents = course.get("registeredStudents") as ArrayList<*>
                        regStudents.forEach { std->
                            db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr").collection("students").whereEqualTo("studentId",std.toString()).get().addOnSuccessListener {res->
                                res.forEach {stdData->
                                    stds.add(students(stdData.getString("profile.name").toString(),std.toString(),stdData.get("attending.$courseId").toString()))
                                    binding.stdList.adapter = DivStudentsAdapter(this,stds)
                                    binding.stdList.layoutManager = LinearLayoutManager(this)
                                }
                            }
                        }
                    }
                }

    }
}