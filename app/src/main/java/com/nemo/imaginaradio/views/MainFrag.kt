package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nemo.imaginaradio.databinding.MainFragBinding

class MainFrag: Fragment(){
    private var _binding: MainFragBinding? = null
    private val binding: MainFragBinding
        get()= checkNotNull(_binding) { "uso incorrecto del objeto binding"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        startBinding(inflater, container)
        return binding.root
    }

    private fun startBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = MainFragBinding.inflate(inflater, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}