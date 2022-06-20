package com.fit.iugaza.edu.ps.qra.instructor.view.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fit.iugaza.edu.ps.qra.instructor.R
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ActivityDivisionStudentsBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.students
import com.fit.iugaza.edu.ps.qra.instructor.view.adapters.DivStudentsAdapter

class DivisionStudents : AppCompatActivity() {
    private var _binding: ActivityDivisionStudentsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDivisionStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val students = arrayListOf<students>()
        students.add(students("مهدي ديب عمر طه", "120192424", "6%"))
        students.add(students("محمد محمود أحمد حامد", "120160000", "20%"))
        students.add(students("سعيد أسعد سعد مسعود", "120180000", "2%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("مهدي ديب عمر طه", "120192424", "6%"))
        students.add(students("محمد محمود أحمد حامد", "120160000", "20%"))
        students.add(students("سعيد أسعد سعد مسعود", "120180000", "2%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("مهدي ديب عمر طه", "120192424", "6%"))
        students.add(students("محمد محمود أحمد حامد", "120160000", "20%"))
        students.add(students("سعيد أسعد سعد مسعود", "120180000", "2%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        students.add(students("فؤاد فايز فارس فادي", "120150000", "9%"))
        binding.apply {
            appbar.title.text = "الطلاب"
            appbar.btnBack.setOnClickListener { finish() }
            stdList.adapter = DivStudentsAdapter(this@DivisionStudents, students)
            stdList.layoutManager = LinearLayoutManager(this@DivisionStudents)
        }
    }
}