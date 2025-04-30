package com.nemo.imaginaradio.models

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PostRepository {
    private val moshi = Moshi.Builder().build()
    private val postApi:PostApi = Retrofit.Builder().baseUrl("https://www.imaginaradio.cat/wp-json/wp/v2/").addConverterFactory(
        MoshiConverterFactory.create(moshi)).build().create(PostApi::class.java)

    val postRawListType = Types.newParameterizedType(List::class.java, PostRaw::class.java)
    val postAdapter = moshi.adapter<List<PostRaw>>(postRawListType)

    //suspend fun getCategoryPosts(category: Int, perPage: Int = 10): List<Post> = postApi.getCategoryPosts(category = category, perPage = perPage)
    suspend fun getCategoryPosts(category: Int, perPage: Int = 10): List<Post>{
        val response: List<PostRaw> = postApi.getCategoryPosts(category = category, perPage = perPage)

        return response.map{ post ->
            Post(
                id = post.id,
                title = post.title.rendered,
                content = post.content.rendered,
                date = post.date,
                categories = post.categories
            )
        }
    }

    //suspend fun getLastPosts(perPage: Int = 10): List<Post> = postApi.getLastPosts(perPage = perPage)
    suspend fun getLastPosts(perPage: Int = 10): List<Post>{
        val response: List<PostRaw> = postApi.getLastPosts(perPage = perPage)

        return response.map{ post ->
            Post(
                id = post.id,
                title = post.title.rendered,
                content = post.content.rendered,
                date = post.date,
                categories = post.categories
            )
        }
    }

    //suspend fun getPostById(id: Int): Post = postApi.getPostById(id)
    suspend fun getPostById(id: Int): Post{
        val response: PostRaw = postApi.getPostById(id = id)

        return Post(
            id = response.id,
            title = response.title.rendered,
            content = response.content.rendered,
            date = response.date,
            categories = response.categories
        )
    }
}