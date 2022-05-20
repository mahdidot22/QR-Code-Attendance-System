package com.fit.iugaza.edu.ps.qra.std.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.std.databinding.CourseItemBinding
import com.fit.iugaza.edu.ps.qra.std.model.course
import com.fit.iugaza.edu.ps.qra.std.view.activities.CourseSchedule

class CoursesAdapter(val context: Context, val courses: ArrayList<course>) :
    RecyclerView.Adapter<CoursesAdapter.CourseHolder>() {
    class CourseHolder(private val itemBinding: CourseItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(course: course, context: Context) {
            itemBinding.btnCourse.text = course.name
            itemBinding.btnCourse.setOnClickListener {
                Constants().navigation(
                    context,
                    CourseSchedule::class.java,
                    itemBinding.btnCourse.text.toString()
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val itemBinding =
            CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {
        val myCourse = courses[position]
        holder.bind(myCourse, context)
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}