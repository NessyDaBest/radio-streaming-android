package com.nemo.imaginaradio.models

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PostRepository {
    private val moshi = Moshi.Builder().build()
    private val postApi:PostApi = Retrofit.Builder().baseUrl("https://www.imaginaradio.cat/wp-json/wp/v2/").addConverterFactory(
        MoshiConverterFactory.create(moshi)).build().create(PostApi::class.java)

    //Post queries
    suspend fun getCategoryPosts(category: Int, perPage: Int = 10): List<Post>{
        return arrayParse(postApi.getCategoryPosts(category = category, perPage = perPage))
    }

    suspend fun getLastPosts(perPage: Int = 10): List<Post>{
        return arrayParse(postApi.getLastPosts(perPage = perPage))
    }

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

    //Media queries
    suspend fun getPostMedia(id: Int): String{
        return postApi.getPostMedia(id = id).media_details.sizes.large.source_url
    }

    //Parsing posts array response
    fun arrayParse(res: List<PostRaw>): List<Post>{
        return res.map{ post ->
            Post(
                id = post.id,
                title = post.title.rendered,
                content = post.content.rendered,
                date = post.date,
                categories = post.categories
            )
        }
    }
}