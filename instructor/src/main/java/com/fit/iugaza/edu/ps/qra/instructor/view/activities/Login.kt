package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
                    Constants().createToast(
                        this@Login,
                        msg.toastText,
                        msg.root,
                        "إملأ الحقول! رجاء"
                    )
                } else {
                    login(edStdId.text.toString())
                }
            }
        }
    }

    private fun login(edStdId: String) {
        if (Constants().isInternetAvailable(this)) {
            binding.dialog.visibility = View.VISIBLE
            binding.loadMsg.visibility = View.VISIBLE
            db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr")
                .collection("instructor").get()
                .addOnSuccessListener {
                    for (doc in it) {
                        val instructorId = doc.getString("instructorId")
                        if (instructorId == edStdId) {
                            SessionMng(this@Login).setId(edStdId, "id")
                            Constants().navigation(this@Login, Container::class.java)
                            binding.dialog.visibility = View.GONE
                            binding.loadMsg.visibility = View.GONE
                            finish()
                            break
                        } else {
                            Constants().createToast(
                                this@Login,
                                binding.msg.toastText,
                                binding.msg.root,
                                "رقم غير صالح"
                            )
                            binding.dialog.visibility = View.GONE
                            binding.loadMsg.visibility = View.GONE
                        }
                    }
                }.addOnFailureListener { msg ->
                    msg.localizedMessage?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                    }
                }
        } else {
            Constants().createToast(
                this,
                binding.msg.toastText,
                binding.msg.root,
                "تأكد من إتصالك بالإنترنت"
            )
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