package com.fit.iugaza.edu.ps.qra.instructor.view.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.iugaza.edu.ps.qra.instructor.databinding.FragmentProfileBinding


class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}