package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.FragmentNewsBinding
import com.nemo.imaginaradio.models.PostRepository
import kotlinx.coroutines.launch

class NewsFragment: Fragment(){
    private lateinit var _binding: FragmentNewsBinding
    private var postRepo = PostRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        fillFirst()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.noticiesRecycler.layoutManager = LinearLayoutManager(requireContext())
        _binding.terresRecycler.layoutManager = LinearLayoutManager(requireContext())
        _binding.esportsRecycler.layoutManager = LinearLayoutManager(requireContext())
        _binding.vidaRecycler.layoutManager = LinearLayoutManager(requireContext())
        _binding.mediambientRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            try{
                
            }
            catch (e: Exception){
                println("Error:" + e.message)
                println(e.printStackTrace())
            }
        }
    }

    //Loading posts
    private fun fetchData(){
        lifecycleScope.launch {
            try {
                val posts = postRepo.getLastPosts(1)
            } catch (e: Exception) {
                println("Error: ${e.message}")
                println(e.printStackTrace())
            }
        }
    }

    fun fillFirst(){
        lifecycleScope.launch {
            try{
                val post = postRepo.getLastPosts(1).get(0)
                _binding.titleText.setText(post.title)
                _binding.subtitleText.text = post.date
                _binding.headerImage.setPostImage(postRepo.getPostMedia(post.mediaId))
            }
            catch (e: Exception) {
                println("Error: ${e.message}")
                println(e.printStackTrace())
            }
        }
    }

    fun ImageView.setPostImage(link: String){
        val loadingDrawable = ContextCompat.getDrawable(context, R.drawable.loading)

        this.load(link){
            crossfade(true)
            //loadingDrawable?.let{ drawable -> placeholder { drawable.asImage(true) }}
            placeholder(R.drawable.loading_svg)
            error(R.drawable.warning)
        }
    }
}