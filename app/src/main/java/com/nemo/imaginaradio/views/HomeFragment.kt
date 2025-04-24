package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import coil3.load
import com.nemo.imaginaradio.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val urlProgramacion =
        "https://www.imaginaradio.cat/wp-content/uploads/2021/10/programacio-im.jpg"

    private val diasSemana = listOf("LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO")
    private var indiceVisible = 0

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

        // Configurar navegación por días
        binding.btnDiaAnterior.setOnClickListener {
            if (indiceVisible > 0) {
                indiceVisible--
                mostrarDias()
            }
        }
        binding.btnDiaSiguiente.setOnClickListener {
            if (indiceVisible + 4 < diasSemana.size) {
                indiceVisible++
                mostrarDias()
            }
        }

        mostrarDias()
    }

    private fun mostrarDias() {
        binding.contenedorDias.removeAllViews()

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

        for (i in indiceVisible until (indiceVisible + 4).coerceAtMost(diasSemana.size)) {
            val dia = diasSemana[i]
            val boton = Button(requireContext()).apply {
                text = if (dia == nombreHoy) "HOY" else dia
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(8, 0, 8, 0) }
            }
            binding.contenedorDias.addView(boton)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}