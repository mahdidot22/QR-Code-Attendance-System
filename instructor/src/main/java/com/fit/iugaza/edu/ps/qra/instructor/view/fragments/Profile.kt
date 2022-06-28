package com.fit.iugaza.edu.ps.qra.instructor.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.instructor.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        val root = binding.root
        binding.btnInfo.setOnClickListener {
            if (binding.llView.visibility == View.VISIBLE) {
                binding.llView.visibility = View.INVISIBLE
            } else {
                binding.llView.visibility = View.VISIBLE
            }
        }
        setProfile()
        return root
    }

    fun setProfile() {
        binding.edStdId.setText(SessionMng(requireContext()).getId("id"))
        db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr").collection("instructor")
            .whereEqualTo("instructorId", SessionMng(requireContext()).getId("id")).get()
            .addOnSuccessListener {
                for (doc in it) {
                    val profile = doc.get("profile") as Map<*, *>
                    binding.edName.setText(profile["name"].toString())
                    binding.edPhone.setText(profile["phone"].toString())
                    binding.edEmail.setText(profile["email"].toString())
                    binding.edAddress.setText(profile["address"].toString())
                }
            }.addOnFailureListener {
                it.localizedMessage?.let { msg ->
                    Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}