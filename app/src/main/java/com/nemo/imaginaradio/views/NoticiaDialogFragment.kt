package com.nemo.imaginaradio.views

import android.os.Bundle
import android.text.Html
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
import com.nemo.imaginaradio.databinding.NoticiaDialogBinding
import com.nemo.imaginaradio.models.Post
import com.nemo.imaginaradio.models.PostRepository
import kotlinx.coroutines.launch

class NoticiaDialogFragment : DialogFragment() {
    lateinit var _binding: NoticiaDialogBinding
    private var postRepo = PostRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NoticiaDialogBinding.inflate(inflater, container, false)
        fetchData()
        return _binding.root
    }

    //Loading post
    private fun fetchData(){
        lifecycleScope.launch {
            try {
                fillView(postRepo.getPostById(135131), postRepo.getPostMedia(135133))
            } catch (e: Exception) {
                println("Error: ${e.message}")
                println(e.printStackTrace())
            }
        }
    }

    fun fillView(post: Post, media: String){
        lifecycleScope.launch {
            _binding.tituloNoticia.setText(Html.fromHtml(post.title, Html.FROM_HTML_MODE_LEGACY))
            _binding.contenidoNoticia.setText(Html.fromHtml(post.content, Html.FROM_HTML_MODE_LEGACY))
            _binding.imagenNoticia.setPostImage(media)
            println(post)
        }
    }

    fun ImageView.setPostImage(link: String){
        //This does jackshit and changes nothing but i'm too tired of it to care any more
        val loadingDrawable = ContextCompat.getDrawable(context, R.drawable.loading)

        this.load(link){
            crossfade(true)
            //Same as before with this line, Kotlin is the most undocumented language i've ever seen
            loadingDrawable?.let{ drawable -> placeholder { drawable.asImage(true) }}
            //error(R.drawable.loading)
        }
    }
}