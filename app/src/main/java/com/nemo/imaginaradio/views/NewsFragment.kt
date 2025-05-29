package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.nemo.imaginaradio.utils.CardPostsAdapter
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

        _binding.noticiesRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        _binding.noticiesRecycler.adapter = CardPostsAdapter(7, viewLifecycleOwner.lifecycleScope, childFragmentManager)

        _binding.terresRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        _binding.terresRecycler.adapter = CardPostsAdapter(6498, viewLifecycleOwner.lifecycleScope, childFragmentManager)

        _binding.esportsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        _binding.esportsRecycler.adapter = CardPostsAdapter(9, viewLifecycleOwner.lifecycleScope, childFragmentManager)

        _binding.vidaRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        _binding.vidaRecycler.adapter = CardPostsAdapter(11, viewLifecycleOwner.lifecycleScope, childFragmentManager)

        _binding.mediambientRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        _binding.mediambientRecycler.adapter = CardPostsAdapter(687, viewLifecycleOwner.lifecycleScope, childFragmentManager)

        _binding.politicaRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        _binding.politicaRecycler.adapter = CardPostsAdapter(8, viewLifecycleOwner.lifecycleScope, childFragmentManager)

    }

    //Loading post
    fun fillFirst(){
        lifecycleScope.launch {
            try{
                val post = postRepo.getLastPosts(1).get(0)
                _binding.titleText.setText(post.title)
                _binding.subtitleText.text = post.date
                _binding.headerImage.setPostImage(postRepo.getPostMedia(post.mediaId))

                _binding.headerContainer.setOnClickListener {
                    PostDialogFragment.newInstance(post.id).show(childFragmentManager, "NoticiaDialog")
                }
            }
            catch (e: Exception) {
                println("Error: ${e.message}" + "from main image")
                println(e.printStackTrace())
            }
        }
    }

    fun ImageView.setPostImage(link: String){
        this.load(link){
            crossfade(true)
            placeholder(R.drawable.loading_svg)
            error(R.drawable.warning)
        }
    }
}