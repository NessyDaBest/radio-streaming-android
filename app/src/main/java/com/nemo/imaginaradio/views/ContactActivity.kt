package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nemo.imaginaradio.databinding.ActivityContactoBinding

class ContactActivity : Fragment() {

    private var _binding: ActivityContactoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityContactoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el Spinner con la lista de departamentos
        val departamentos = listOf("Redacción", "Web", "Radio", "Publicidad", "Administración")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, departamentos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDepartamento.adapter = adapter

        // Listener para el botón de Enviar
        binding.btnEnviar.setOnClickListener {
            // Aquí podrías validar los datos y enviar el mensaje, en este ejemplo se muestra un Toast.
            Toast.makeText(requireContext(), "Mensaje enviado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}