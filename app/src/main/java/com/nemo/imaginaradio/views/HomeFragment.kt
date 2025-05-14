package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import coil3.load
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.FragmentHomeBinding
import com.nemo.imaginaradio.model.Programa
import com.nemo.imaginaradio.utils.RadioPlayer
import com.nemo.imaginaradio.viewmodels.PlayerViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val playerViewModel: PlayerViewModel by activityViewModels()

    private val urlProgramacion =
        "https://www.imaginaradio.cat/wp-content/uploads/2021/10/programacio-im.jpg"

    private val diasSemana = listOf("LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO")

    private val mapProgramas = mapOf(
        "LUNES" to listOf(
            Programa("8:00h", "De Bon Matí", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1638262526/debonmati.jpg?tr=h-300,fo-auto")
        ),
        "MARTES" to listOf(
            Programa("8:00h", "De Bon Matí", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1638262526/debonmati.jpg?tr=h-300,fo-auto")
        ),
        "MIÉRCOLES" to listOf(
            Programa("8:00h", "De Bon Matí", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1638262526/debonmati.jpg?tr=h-300,fo-auto")
        ),
        "JUEVES" to listOf(
            Programa("8:00h", "De Bon Matí", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1638262526/debonmati.jpg?tr=h-300,fo-auto"),
            Programa("21:00h", "Les coses clares", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1741362408/Captura_de_pantalla_2025-03-07_a_las_16.46.11.png?tr=h-300,fo-auto")
        ),
        "VIERNES" to listOf(
            Programa("8:00h", "De Bon Matí", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1638262526/debonmati.jpg?tr=h-300,fo-auto")
        ),
        "SÁBADO" to emptyList(),
        "DOMINGO" to listOf(
            Programa("10:00h", "Cocodril Club", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1638263425/COCODRIL-CLUB.jpg?tr=h-300,fo-auto"),
            Programa("12:00h", "Camí a Eurovisió", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1743080584/2s6WzouN_400x400.jpg?tr=h-300,fo-auto"),
            Programa("16:00h - 20:00h", "Tots els Gols", null, "https://ik.imagekit.io/7ftrkrun31/enacast/logos_programes/1638262770/tots_elsgols.jpg?tr=h-300,fo-auto")
        )
    )

    private lateinit var adapterDias: DiasAdapter
    private lateinit var adapterProgramas: ProgramaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        inicializarReproductor()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerDias()
        setupRecyclerProgramas("HOY")
    }

    private fun setupRecyclerDias() {
        val hoy = diasSemana[(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) + 5) % 7]
        val listaDias = diasSemana.map { if (it == hoy) "HOY" else it }
        adapterDias = DiasAdapter(listaDias) { diaSeleccionado ->
            val clave = if (diaSeleccionado == "HOY") hoy else diaSeleccionado
            setupRecyclerProgramas(clave)
        }
        binding.recyclerDias.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerDias.adapter = adapterDias
    }

    private fun setupRecyclerProgramas(dia: String) {
        val lista = mapProgramas[dia].orEmpty()
        if (lista.isEmpty()) {
            val mensaje = Programa("", "Avui no hi ha programes", "", "")
            adapterProgramas = ProgramaAdapter(listOf(mensaje))
        } else {
            adapterProgramas = ProgramaAdapter(lista)
        }
        binding.recyclerProgramacion.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerProgramacion.adapter = adapterProgramas
    }

    private fun inicializarReproductor() {
        RadioPlayer.initialize(playerViewModel,binding.playButton)
        binding.reproductorEnVivo.setOnClickListener {
            requireActivity().findViewById<ConstraintLayout>(R.id.main).visibility = View.GONE
            requireActivity().findViewById<FragmentContainerView>(R.id.player_container).visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}