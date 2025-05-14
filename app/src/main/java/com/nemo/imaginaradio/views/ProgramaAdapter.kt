package com.nemo.imaginaradio.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.*
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import coil3.request.target
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.model.Programa

class ProgramaAdapter(private var programas: List<Programa>) :
    RecyclerView.Adapter<ProgramaAdapter.ProgramaViewHolder>() {

    inner class ProgramaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hora: TextView = view.findViewById(R.id.tvHora)
        val titulo: TextView = view.findViewById(R.id.tvTitulo)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val imagen: ImageView = view.findViewById(R.id.imgPrograma)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_programa, parent, false)
        return ProgramaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramaViewHolder, position: Int) {
        val programa = programas[position]
        holder.hora.text = programa.hora
        holder.titulo.text = programa.titulo
        holder.descripcion.text = programa.descripcion ?: ""

        // Carga de imagen desde URL usando Coil
        val context = holder.imagen.context
        val request = ImageRequest.Builder(context)
            .data(programa.imagenUrl)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_background)
            .target(holder.imagen)
            .build()

        coil3.ImageLoader(context).enqueue(request)
    }

    override fun getItemCount(): Int = programas.size

    fun actualizarProgramas(nuevos: List<Programa>) {
        programas = nuevos
        notifyDataSetChanged()
    }
}
