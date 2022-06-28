package com.fit.iugaza.edu.ps.qra.instructor.view.adapters
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.CourseItemBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.course
import com.fit.iugaza.edu.ps.qra.instructor.view.activities.CourseSchedule

class CoursesAdapter(val context: Context, val courses: ArrayList<course>) :
    RecyclerView.Adapter<CoursesAdapter.CourseHolder>() {
    class CourseHolder(private val itemBinding: CourseItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(course: course, context: Context) {
            itemBinding.btnCourse.text = course.name + " شعبة رقم ${course.division}"
            itemBinding.btnCourse.setOnClickListener {
                Constants().navigation(context,CourseSchedule::class.java,course.id,course.division,course.name)
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