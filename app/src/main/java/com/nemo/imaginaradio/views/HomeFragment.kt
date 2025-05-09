package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import com.nemo.imaginaradio.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val urlProgramacion =
        "https://www.imaginaradio.cat/wp-content/uploads/2021/10/programacio-im.jpg"

    private val diasSemana = listOf("LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar imagen flotante
        /*
        binding.btnVerProgramacionCompleta.setOnClickListener {
            binding.fondoFlotante.visibility = View.VISIBLE
            binding.imgProgramacionCompleta.visibility = View.VISIBLE
            binding.imgProgramacionCompleta.load(urlProgramacion)
        }

        val ocultarImagen = View.OnClickListener {
            binding.fondoFlotante.visibility = View.GONE
            binding.imgProgramacionCompleta.visibility = View.GONE
        }
        binding.fondoFlotante.setOnClickListener(ocultarImagen)
        binding.imgProgramacionCompleta.setOnClickListener(ocultarImagen)
        */
        // Configurar RecyclerView de días
        setupRecyclerDias()
    }

    private fun setupRecyclerDias() {
        val hoy = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val mapDiaSemana = mapOf(
            Calendar.MONDAY to "LUNES",
            Calendar.TUESDAY to "MARTES",
            Calendar.WEDNESDAY to "MIÉRCOLES",
            Calendar.THURSDAY to "JUEVES",
            Calendar.FRIDAY to "VIERNES",
            Calendar.SATURDAY to "SÁBADO",
            Calendar.SUNDAY to "DOMINGO"
        )

        val nombreHoy = mapDiaSemana[hoy]

        val listaDias = diasSemana.map { dia ->
            if (dia == nombreHoy) "HOY" else dia
        }

        val adapter = DiasAdapter(listaDias)
        binding.recyclerDias.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDias.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
