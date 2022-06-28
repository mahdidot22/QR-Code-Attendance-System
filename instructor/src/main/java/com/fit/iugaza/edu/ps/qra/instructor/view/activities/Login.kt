package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log


class Login : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Constants().statusBarColor(this)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnLogin.setOnClickListener {
                if (edStdId.text.isNullOrEmpty()) {
                    Toast.makeText(this@Login, "إملأ الحقول! رجاءً", Toast.LENGTH_SHORT).show()
                } else {
                    login(edStdId.text.toString())
                }
            }
        }
    }

    private fun login(edStdId:String){
        db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr")
            .collection("instructor").get()
            .addOnSuccessListener {
                for (doc in it) {
                    val instructorId = doc.getString("instructorId")
                    if (instructorId == edStdId){
                        SessionMng(this@Login).setId(edStdId,"id")
                        Constants().navigation(this@Login,Container::class.java)
                        finish()
                        break
                    }else{
                        Toast.makeText(this@Login, "رقم غير صالح", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener { msg ->
                msg.localizedMessage?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }
    }
    override fun onStart() {
        super.onStart()
        if (!SessionMng(this).getId("id").isNullOrEmpty()) {
            Constants().navigation(this, Container::class.java)
            finish()
        }
    }
}