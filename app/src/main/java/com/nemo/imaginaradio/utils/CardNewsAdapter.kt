package com.nemo.imaginaradio.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.LifecycleCoroutineScope
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.CardPostsBinding
import com.nemo.imaginaradio.models.Post
import com.nemo.imaginaradio.models.PostRepository
import com.nemo.imaginaradio.views.NoticiaDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardPostsHolder(
    private val binding: CardPostsBinding,
    private val lifecycleScope: LifecycleCoroutineScope
): RecyclerView.ViewHolder(binding.root){
    private val postRepo: PostRepository = PostRepository()

    fun bind(item: Post){
        binding.postTitle.text = item.title
        binding.postImage.setImageResource(R.drawable.loading_svg)

        lifecycleScope.launch {
            try{
                val image = postRepo.getPostMedia(item.mediaId)

                withContext(Dispatchers.Main){
                    binding.postImage.load(image){
                        crossfade(true)
                        placeholder(R.drawable.loading_svg)
                        error(R.drawable.warning)
                    }
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    binding.postImage.setImageResource(R.drawable.warning)
                }

                println("Error:" + e.message)
                println(e.printStackTrace())
            }
        }
    }
}

class CardPostsAdapter(
    private val category: Int,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val fragmentManager: FragmentManager
): RecyclerView.Adapter<CardPostsHolder>(){
    private var posts: List<Post> = emptyList()

    init {
        loadPosts(category)
    }

    private fun loadPosts(category: Int){
        lifecycleScope.launch {
            try{
                val response = PostRepository().getCategoryPosts(category, 7, 1)

                if (category == 7){ response.drop(1) }
                withContext(Dispatchers.Main) {
                    posts = response
                    notifyDataSetChanged()
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    //Put something here says the AI
                }
                println("Error:" + e.message)
                println(e.printStackTrace())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPostsHolder {
        val binding = CardPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardPostsHolder(binding, lifecycleScope)
    }

    override fun onBindViewHolder(holder: CardPostsHolder, position: Int) {
        holder.bind(posts[position])
        holder.itemView.setOnClickListener {
            //showPostDialog(holder.itemView.context, posts[position].id)
            val dialog = NoticiaDialogFragment.newInstance(posts[position].id)
            dialog.show(fragmentManager, "NoticiaDialog")
        }
    }

    private fun showPostDialog(context: Context, post: Int){

    }

    override fun getItemCount(): Int = posts.size
}