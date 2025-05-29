package com.nemo.imaginaradio.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.ItemCardPostsBinding
import com.nemo.imaginaradio.models.Post
import com.nemo.imaginaradio.models.PostRepository
import com.nemo.imaginaradio.views.PostDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardPostsHolder(
    private val binding: ItemCardPostsBinding,
    private val lifecycleScope: LifecycleCoroutineScope
): RecyclerView.ViewHolder(binding.root){
    private val postRepo: PostRepository = PostRepository()

    fun bind(item: Post){
        binding.postTitle.text = item.title
        binding.postImage.setImageResource(R.drawable.loading_svg)

        lifecycleScope.launch {
            try{
                val image = postRepo.getPostMedia(item.mediaId, true)

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

                println("Error:" + e.message + " from post " + item.id)
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
    private var posts = mutableListOf<Post>()
    private var currPage = 1
    private var isLoading = false
    private var isInitialLoad = true

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    init {
        loadPosts(category)
    }

    private fun loadPosts(category: Int, page: Int = 1){
        lifecycleScope.launch {
            try{
                var response = PostRepository().getCategoryPosts(category, 7, currPage)
                if (category == 7 && isInitialLoad){
                    response = response.drop(1)
                }

                withContext(Dispatchers.Main) {
                    if (isLoading) {
                        isLoading = false
                    }

                    if (isInitialLoad || page == 1) {
                        posts = response.toMutableList()
                        notifyDataSetChanged()
                        isInitialLoad = false
                    }
                    else{
                        val startPosition = posts.size
                        posts.addAll(response)
                        notifyItemRangeInserted(startPosition, response.size)
                    }
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    if (isLoading) {
                        isLoading = false
                    }
                }
                println("Error:" + e.message)
                println(e.printStackTrace())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPostsHolder {
        val binding = ItemCardPostsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardPostsHolder(binding, lifecycleScope)
    }

    override fun onBindViewHolder(holder: CardPostsHolder, position: Int) {
        holder.bind(posts[position])
        holder.itemView.setOnClickListener {
            PostDialogFragment.newInstance(posts[position].id).show(fragmentManager, "NoticiaDialog")
        }
    }

    override fun getItemViewType(pos: Int): Int{
        return if(pos == posts.size && isLoading) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return posts.size// + if (isLoading) 1 else 0
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int){
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleCount = layoutManager.childCount
                val totalCardCount = layoutManager.itemCount
                val firstVisibleCardPos = layoutManager.findFirstVisibleItemPosition()

                //println("" + visibleCount + " " + firstVisibleCardPos + " " + (totalCardCount - 5))
                if (visibleCount + firstVisibleCardPos >= totalCardCount - 5) {
                    loadMoreItems()
                }
            }
        })
    }

    private fun loadMoreItems() {
        if (!isLoading) {
            isLoading = true
            currPage++
            loadPosts(category, currPage)
        }
    }
}