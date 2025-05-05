package com.nemo.imaginaradio.views

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.FragmentPlayerBinding
import com.nemo.imaginaradio.utils.RadioPlayer
import com.nemo.imaginaradio.viewmodels.PlayerViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PlayerFragment : Fragment() {

    var _binding:FragmentPlayerBinding? = null
    val binding:FragmentPlayerBinding
        get() = checkNotNull(_binding)

    private val playerViewModel: PlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeBinding(inflater,container)
        initializeView()
        return binding.root
    }

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentPlayerBinding.inflate(inflater,container,false)
    }

    private fun initializeView() {
        RadioPlayer.initialize(playerViewModel,binding.imgState)
        initializeTime()
        initializeClose()
    }

    private fun initializeTime() {
        playerViewModel.progress.observe(viewLifecycleOwner) { progress ->
            binding.pbMinutes.progress = progress
        }
        playerViewModel.actualHour.observe(viewLifecycleOwner) { actualHour ->
            binding.txtHourA.setText(actualHour)
        }
        playerViewModel.nextHour.observe(viewLifecycleOwner) { nextHour ->
            binding.txtHourB.setText(nextHour)
        }
    }

    private fun initializeClose() {
        binding.imgClose.setOnClickListener {
            requireActivity().findViewById<ConstraintLayout>(R.id.main).visibility = View.VISIBLE
            requireActivity().findViewById<FragmentContainerView>(R.id.player_container).visibility = View.GONE
        }
    }
}