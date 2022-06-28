package com.fit.iugaza.edu.ps.qra.instructor.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.iugaza.edu.ps.qra.instructor.databinding.DivStdItemBinding
import com.fit.iugaza.edu.ps.qra.instructor.model.students

class DivStudentsAdapter(val context: Context, val list: ArrayList<students>) :
    RecyclerView.Adapter<DivStudentsAdapter.DivStdVH>() {
    class DivStdVH(val itemBinding: DivStdItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(student: students) {
            itemBinding.tvStdName.text = student.name
            itemBinding.tvStdId.text = student.id
            itemBinding.tvMiss.text = student.attendedLectures
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivStdVH {
        val itemBinding = DivStdItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return DivStdVH(itemBinding)
    }

    override fun onBindViewHolder(holder: DivStdVH, position: Int) {
        val student = list[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}