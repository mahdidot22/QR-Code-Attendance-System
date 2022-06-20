package com.fit.iugaza.edu.ps.qra.instructor.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.iugaza.edu.ps.qra.constants.Constants
import com.fit.iugaza.edu.ps.qra.instructor.databinding.DivisionItemBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.divisions
import com.fit.iugaza.edu.ps.qra.instructor.view.activities.DivisionStudents

class DivisionsAdapter(val context: Context, val list: ArrayList<divisions>) :
    RecyclerView.Adapter<DivisionsAdapter.DivisionsVH>() {
    class DivisionsVH(val itemBinding: DivisionItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(division: divisions, context: Context) {
            itemBinding.apply {
                tvCourse.text = division.courseName
                tvDivision.text = division.division
                btnShowStds.setOnClickListener {
                    Constants().navigation(context,DivisionStudents::class.java)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivisionsVH {
        val itemBinding = DivisionItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return DivisionsVH(itemBinding)
    }

    override fun onBindViewHolder(holder: DivisionsVH, position: Int) {
        val division = list[position]
        holder.bind(division, context)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}