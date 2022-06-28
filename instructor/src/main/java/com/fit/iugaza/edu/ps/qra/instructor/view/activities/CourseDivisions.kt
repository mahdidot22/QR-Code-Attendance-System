package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityCourseDevisionsBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.divisions
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.DivisionsAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CourseDivisions : AppCompatActivity() {
    private var _binding: ActivityCourseDevisionsBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Constants().statusBarColor(this)
        _binding = ActivityCourseDevisionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            appbar.title.text = "عرض الشعب"
            appbar.btnBack.setOnClickListener { finish() }
        }
        getDivisions()
    }

    private fun getDivisions() {
        val divisions = arrayListOf<divisions>()
        db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr").collection("instructor")
            .whereEqualTo("instructorId", SessionMng(this).getId("id")).get().addOnSuccessListener {
                it.forEach { data ->
                    val courses = data.get("courses") as ArrayList<*>
                    courses.forEach { course ->
                        db.collection("QRAcourses").whereEqualTo("courseId", course.toString())
                            .get().addOnSuccessListener { divisionData ->
                            divisionData.forEach { division ->
                                divisions.add(
                                    divisions(
                                        division.getString("courseId").toString(),
                                        division.getString("courseName").toString(),
                                        division.getString("divisionId").toString()
                                    )
                                )
                                binding.divisionsList.adapter = DivisionsAdapter(this,divisions)
                                binding.divisionsList.layoutManager = LinearLayoutManager(this)
                            }
                        }
                    }
                }
            }
    }
}