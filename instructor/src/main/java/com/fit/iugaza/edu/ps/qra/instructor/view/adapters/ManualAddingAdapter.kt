package com.fit.iugaza.edu.ps.qra.instructor.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ManualItemBinding


class ManualAddingAdapter(val context: Context) :
    RecyclerView.Adapter<ManualAddingAdapter.ManualViewHolder>() {
    private val list: ArrayList<Int> = arrayListOf(1)
    class ManualViewHolder(private val manualItemBinding: ManualItemBinding) :
        RecyclerView.ViewHolder(manualItemBinding.root) {
        fun bind(list: ArrayList<Int>, manualAddingAdapter: ManualAddingAdapter) {
            manualItemBinding.apply {
                manualItemBinding.btnMore.setOnClickListener {
                    if (adapterPosition == list.size -1){
                        list.add(1)
                        manualAddingAdapter.notifyItemInserted(adapterPosition+1)
                    }
                }
                manualItemBinding.btnSave.setOnClickListener { }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManualViewHolder {
        val manualItemBinding =
            ManualItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManualViewHolder(manualItemBinding)
    }

    override fun onBindViewHolder(holder: ManualViewHolder, position: Int) {
        holder.bind(list,this)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}