package com.nemo.imaginaradio.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.FragmentFloatingPlayerBinding
import com.nemo.imaginaradio.databinding.FragmentHomeBinding
import com.nemo.imaginaradio.utils.RadioPlayer
import com.nemo.imaginaradio.viewmodels.PlayerViewModel

class FloatingPlayerFragment : Fragment() {
    private var _binding: FragmentFloatingPlayerBinding? = null
    private val binding get() = _binding!!

    private val playerViewModel: PlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFloatingPlayerBinding.inflate(inflater, container, false)
        inicializarReproductor()
        return binding.root
    }

    private fun inicializarReproductor() {
        RadioPlayer.initialize(playerViewModel,binding.playPauseButton,viewLifecycleOwner)
        binding.floatingPlayerContainer.setOnClickListener {
            requireActivity().findViewById<ConstraintLayout>(R.id.main).visibility = View.GONE
            requireActivity().findViewById<FragmentContainerView>(R.id.player_container).visibility = View.VISIBLE
        }
    }
}