package com.fit.iugaza.edu.ps.qra.std.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.std.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnLogin.setOnClickListener {
                if (edStdId.text.isNullOrEmpty()) {
                    Constants().createToast(
                        this@Login,
                        msg.toastText,
                        msg.root,
                        "إملأ الحقول!"
                    )
                } else {
                    userLogin(edStdId.text.toString())
                }
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

    fun userLogin(stdId: String) {
        val mac = Constants().deviceId(this)
        if (Constants().isInternetAvailable(this)){
            binding.dialog.visibility = View.VISIBLE
            binding.loadMsg.visibility = View.VISIBLE
            db.collection("QRAUser")
                .document("oGa1XzI9d2YsOOFIjBRr").collection("students").get()
                .addOnSuccessListener { result ->
                    for (doc in result) {
                        if (doc.getString("mac") == mac && stdId == doc.getString("studentId")) {
                            SessionMng(this@Login).setId(stdId, "id")
                            Constants().navigation(
                                this@Login,
                                Container::class.java
                            )
                            binding.dialog.visibility = View.GONE
                            binding.loadMsg.visibility = View.GONE
                            finish()
                            break
                        } else if (doc.getString("mac") != mac && stdId != doc.getString("studentId")) {
                            firstTimeLogin(stdId, mac)
                            break
                        } else if (doc.getString("mac") == mac && stdId != doc.getString("studentId")) {
                            Constants().createToast(
                                this@Login,
                                binding.msg.toastText,
                                binding.msg.root,
                                "انتبه! هذا الجهاز تم التسجيل عليه مسبقاً، يرجى إدخال الرقم الصحيح لهذا الجهاز."
                            )
                            binding.dialog.visibility = View.GONE
                            binding.loadMsg.visibility = View.GONE
                            break
                        } else if (doc.getString("mac") != mac && stdId == doc.getString("studentId")) {
                            binding.dialog.visibility = View.GONE
                            binding.loadMsg.visibility = View.GONE
                            Constants().createToast(
                                this@Login,
                                binding.msg.toastText,
                                binding.msg.root,
                                "عذراً لا يمكنك القيام بذلك! أنت تحاول الدخول إلى حسابك من جهاز اخر."
                            )
                            break // fix bug
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    exception.localizedMessage?.let {
                        Snackbar.make(
                            binding.root,
                            it,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
        }else{
            Constants().createToast(
                this,
                binding.msg.toastText,
                binding.msg.root,
                "تأكد من إتصالك بالإنترنت"
            )
        }
    }

    fun firstTimeLogin(studentId: String, mac: String) {
        val student = hashMapOf(
            "studentId" to studentId,
            "mac" to mac,
        )
        db.collection("QRAUser")
            .document("oGa1XzI9d2YsOOFIjBRr").collection("students").add(student)
            .addOnSuccessListener {
                SessionMng(this@Login).setId(studentId, "id")
                Constants().navigation(
                    this@Login,
                    Container::class.java
                )
                binding.dialog.visibility = View.GONE
                binding.loadMsg.visibility = View.GONE
                finish()
            }
            .addOnFailureListener { e ->
                e.localizedMessage?.let {
                    Snackbar.make(
                        binding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }
}