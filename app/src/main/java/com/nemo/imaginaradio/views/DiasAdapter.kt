package com.nemo.imaginaradio.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.nemo.imaginaradio.R

class DiasAdapter(
    private val dias: List<String>,
    private val onDiaClick: (String) -> Unit
) : RecyclerView.Adapter<DiasAdapter.DiaViewHolder>() {

    inner class DiaViewHolder(val button: Button) : RecyclerView.ViewHolder(button)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.simple_button, parent, false) as Button
        return DiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaViewHolder, position: Int) {
        holder.button.text = dias[position]
        holder.button.setOnClickListener {
            onDiaClick(dias[position])
        }
    }

    override fun getItemCount(): Int = dias.size
}
