package com.nemo.imaginaradio.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.LifecycleCoroutineScope
import com.nemo.imaginaradio.R
import com.nemo.imaginaradio.databinding.CardPostsBinding
import com.nemo.imaginaradio.models.Post
import com.nemo.imaginaradio.models.PostRepository

//class CardPostsAdapter(private val posts: List<Post>): RecyclerView.Adapter<CardNewsHolder>(){}

class CardNewsHolder(private val binding: CardPostsBinding, private val lifecycleScope: LifecycleCoroutineScope): RecyclerView.ViewHolder(binding.root){
    private val postRepo: PostRepository = PostRepository()

    fun bind(item: Post){
        binding.postTitle.text = item.title
        binding.postImage.setImageResource(R.drawable.loading_svg)


    }
}

