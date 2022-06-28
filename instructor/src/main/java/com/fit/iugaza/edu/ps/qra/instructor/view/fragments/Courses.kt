package com.fit.iugaza.edu.ps.qra.instructor.view.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.constants.SessionMng
import com.fit.iugaza.edu.ps.qra.instructor.databinding.FragmentCoursesBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.course
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.CoursesAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Courses : Fragment() {
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val db = Firebase.firestore
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root = binding.root
        CoursesList(db)
        return root
    }
    fun CoursesList(db: FirebaseFirestore) {
        db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr").collection("instructor")
            .whereEqualTo("instructorId", SessionMng(requireContext()).getId("id")).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val courses = document.get("courses") as ArrayList<*>
                    val list = arrayListOf<course>()
                    for (course in courses) {
                        db.collection("QRAcourses").whereEqualTo("courseId", course.toString()).get()
                            .addOnSuccessListener {
                                for (c in it) {
                                    list.add(
                                        course(
                                            c.getString("courseName")!!,
                                            c.getString("courseId")!!,
                                            c.getString("divisionId")!!
                                        )
                                    )
                                    binding.rvCourses.adapter =
                                        CoursesAdapter(requireContext(), list)
                                    binding.rvCourses.layoutManager =
                                        LinearLayoutManager(requireContext())
                                }
                            }.addOnFailureListener { msg ->
                                msg.localizedMessage?.let {
                                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }.addOnFailureListener { msg ->
                msg.localizedMessage?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}