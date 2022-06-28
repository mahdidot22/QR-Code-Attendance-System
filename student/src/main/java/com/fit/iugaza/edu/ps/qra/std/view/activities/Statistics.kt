package com.fit.iugaza.edu.ps.qra.std.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.std.databinding.ActivityStatisticsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Statistics : AppCompatActivity() {
    var _binding: ActivityStatisticsBinding? = null
    val binding get() = _binding!!
    val db = Firebase.firestore
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
        getStatistics()
    }

    @SuppressLint("SetTextI18n")
    private fun getStatistics() {
        db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr").collection("students")
            .whereEqualTo("studentId", SessionMng(this).getId("id")).get().addOnSuccessListener {
                for (doc in it) {
                    val attending = doc.get("attending") as Map<*, *>
                    val number = attending["${intent.getStringExtra("courseId")}"].toString()
                    binding.apply {
                        attendedLectures.text = number
                        percent.text = "${( attendedLectures.text.toString().toFloat() / totalLectures.text.toString().toFloat()) * 100}%"
                    }
                }
            }.addOnFailureListener { it ->
                it.localizedMessage?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}