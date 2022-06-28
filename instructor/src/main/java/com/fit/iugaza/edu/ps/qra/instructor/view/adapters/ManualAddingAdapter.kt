package com.fit.iugaza.edu.ps.qra.instructor.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fit.iugaza.edu.ps.qra.instructor.databinding.ManualItemBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ManualAddingAdapter(val context: Context, val courseId: String) :
    RecyclerView.Adapter<ManualAddingAdapter.ManualViewHolder>() {
    private val list: ArrayList<Int> = arrayListOf(1)
    private val db = Firebase.firestore

    class ManualViewHolder(private val manualItemBinding: ManualItemBinding) :
        RecyclerView.ViewHolder(manualItemBinding.root) {
        fun bind(
            list: ArrayList<Int>,
            manualAddingAdapter: ManualAddingAdapter,
            context: Context,
            db: FirebaseFirestore,
            courseId: String
        ) {
            manualItemBinding.apply {
                btnMore.setOnClickListener {
                    if (adapterPosition == list.size - 1) {
                        list.add(1)
                        manualAddingAdapter.notifyItemInserted(adapterPosition + 1)
                    }
                }
                btnSave.setOnClickListener {
                    if (!edStdId.text.isNullOrEmpty()) {
                        db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr")
                            .collection("students")
                            .whereEqualTo("studentId", edStdId.text.toString()).whereArrayContains("courses",courseId).get()
                            .addOnSuccessListener {
                                for (doc in it) {
                                    db.collection("QRAUser").document("oGa1XzI9d2YsOOFIjBRr")
                                        .collection("students").document(doc.id)
                                        .update("attending.$courseId", FieldValue.increment(1))
                                    break
                                }
                            }
                            .addOnFailureListener { msg ->
                                msg.localizedMessage?.let {
                                    Snackbar.make(manualItemBinding.root,it,Snackbar.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "إملاً الحقول رجاءً", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManualViewHolder {
        val manualItemBinding =
            ManualItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManualViewHolder(manualItemBinding)
    }

    override fun onBindViewHolder(holder: ManualViewHolder, position: Int) {
        holder.bind(list, this, context,db,courseId)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}