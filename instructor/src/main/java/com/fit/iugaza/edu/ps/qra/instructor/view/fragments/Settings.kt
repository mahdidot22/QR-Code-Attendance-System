package com.fit.iugaza.edu.ps.qra.instructor.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.instructor.databinding.FragmentSettingsBinding
import com.fit.iugaza.edu.ps.qra.instructor.view.activities.Login

class Settings : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        val root = binding.root
        binding.fbLogout.setOnClickListener {
            Constants().showDialog(
                requireContext(),
                android.R.drawable.ic_dialog_info,
                "تسجيل الخروج",
                "هل أنت متأكد؟",
                "نعم",
                "إغلاق",
                { _, _ ->
                    run {
                        SessionMng(requireActivity()).logout()
                        Constants().navigation(requireActivity(), Login::class.java)
                        requireActivity().finish()
                    }
                },
                { _, _ -> })
        }
        binding.scDark.setOnCheckedChangeListener { _, isChecked ->
            Constants().darkNight(isChecked)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}