package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil3.imageLoader
import coil3.request.*
import com.nemo.imaginaradio.databinding.FragmentProgBinding


class ProgFragment : Fragment() {

    private var _binding: FragmentProgBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = ImageRequest.Builder(requireContext())
            .data("https://www.imaginaradio.cat/wp-content/uploads/2021/10/programacio-im.jpg")
            .crossfade(true)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_delete)
            .target(binding.imagenProgramacion)
            .build()

        requireContext().imageLoader.enqueue(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
