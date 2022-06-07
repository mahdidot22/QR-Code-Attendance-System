package com.fit.iugaza.edu.ps.qra.instructor.view.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.instructor.databinding.FragmentCoursesBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.course
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.CoursesAdapter


class Courses : Fragment() {
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root = binding.root
        val courses = arrayListOf<course>()
        courses.add((course("مشروع التخرج")))
        courses.add((course("برمجة الألعاب للأجهزة الذكية")))
        courses.add((course("اللغة العبرية")))
        courses.add((course("لغة البرمجة1")))
        courses.add((course("مهارات الإتصال وريادة الأعمال")))
        binding.rvCourses.adapter = CoursesAdapter(requireContext(), courses)
        binding.rvCourses.layoutManager = LinearLayoutManager(activity)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}