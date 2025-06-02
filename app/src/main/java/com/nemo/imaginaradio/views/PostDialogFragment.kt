package com.nemo.imaginaradio.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import coil3.asImage
import coil3.load
import coil3.request.crossfade
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.PostDialogBinding
import com.nemo.imaginaradio.models.Post
import com.nemo.imaginaradio.models.PostRepository
import kotlinx.coroutines.launch

class PostDialogFragment : DialogFragment() {
    private lateinit var _binding: PostDialogBinding
    private var postRepo = PostRepository()

    companion object {
        private const val ARG_POST_ID = "arg_post_id"

        fun newInstance(postId: Int): PostDialogFragment{
            val fragment = PostDialogFragment()
            val args = Bundle()
            args.putInt(ARG_POST_ID, postId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostDialogBinding.inflate(inflater, container, false)

        val postId = arguments?.getInt(ARG_POST_ID)?: null
        if (postId != null){
            fetchData(postId)
        }
        else{
            //Do something here
        }

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.btnCerrar.setOnClickListener{ dismiss() }
    }

    //Loading post
    private fun fetchData(post: Int){
        lifecycleScope.launch {
            try {
                val post = postRepo.getPostById(post)
                val media = postRepo.getPostMedia(post.mediaId)
                fillView(post, media)
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun fillView(post: Post, media: String){
        lifecycleScope.launch {
            _binding.tituloNoticia.setText(post.title)
            _binding.fechaNoticia.setText(post.date)
            _binding.contenidoNoticia.setText(post.content)
            _binding.imagenNoticia.setPostImage(media)
        }
    }

    fun ImageView.setPostImage(link: String){
        //This does jackshit and changes nothing but i'm too tired of it to care any more
        val loadingDrawable = ContextCompat.getDrawable(context, R.drawable.loading_svg)

        this.load(link){
            crossfade(true)
            //Same as before with this line, Kotlin is the most undocumented language i've ever seen
            loadingDrawable?.let{ drawable -> placeholder { drawable.asImage(true) }}
            //error(R.drawable.warning)
        }
    }
}