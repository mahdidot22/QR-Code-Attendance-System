package com.fit.iugaza.edu.ps.qra.std.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.std.R
import com.fit.iugaza.edu.ps.qra.std.databinding.CourseAppoitmentItemBinding
import com.fit.iugaza.edu.ps.qra.std.model.course_appointment
import com.fit.iugaza.edu.ps.qra.std.view.activities.QrScanning
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CoursesAppointmentsAdapter(val context: Context, val courses: ArrayList<course_appointment>) :
    RecyclerView.Adapter<CoursesAppointmentsAdapter.CourseAppointmentHolder>() {
    class CourseAppointmentHolder(private val itemBinding: CourseAppoitmentItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(course: course_appointment, context: Context) {
            itemBinding.apply {
                tvDay.text = course.day
                tvTime.text = course.time
                tvDivision.text = course.division
                checkDate(tvDay.text.toString(), btnQr,context)
            }
        }

        private fun checkDate(day: String, btnQr: ImageView,context: Context) {
            val date = Date()
            val sdf = SimpleDateFormat("EEEE", Locale("ar"))
            val dayOfTheWeek = sdf.format(date)
            if (day == dayOfTheWeek) {
                btnQr.setImageResource(R.drawable.ic_qr)
                btnQr.setOnClickListener { Constants().navigation(context, QrScanning::class.java) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseAppointmentHolder {
        val itemBinding =
            CourseAppoitmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseAppointmentHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CourseAppointmentHolder, position: Int) {
        val myCourse = courses[position]
        holder.bind(myCourse, context)
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}