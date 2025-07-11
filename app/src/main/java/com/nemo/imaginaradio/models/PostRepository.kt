package com.nemo.imaginaradio.models

import android.R
import android.text.Html
import com.squareup.moshi.Moshi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.collections.mutableMapOf

class PostRepository {
    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    private val moshi = Moshi.Builder().build()
    private val postApi:PostApi = Retrofit.Builder().baseUrl("https://www.imaginaradio.cat/wp-json/wp/v2/").addConverterFactory(
        MoshiConverterFactory.create(moshi)).build().create(PostApi::class.java)

    //Early caches
    private val catCache = mutableMapOf<Int, List<Post>>()
    private var firstCache: List<Post> = emptyList()

    //Post queries
    //page must be one or greater, i'm lazy to write checks
    suspend fun getCategoryPosts(category: Int, perPage: Int = 10, page: Int = 1): List<Post>{
        if (page == 1 && catCache.containsKey(category)) {
            return catCache[category]!!
        }

        val posts = arrayParse(postApi.getCategoryPosts(category = category, perPage = perPage, page = page))
        if(page == 1){
            catCache[category] = posts
        }

        return posts
    }

    //page must be one or greater, i'm lazy to write checks
    suspend fun getLastPosts(perPage: Int = 10, page: Int = 1): List<Post>{
        if(perPage == 1 && page == 1 && !firstCache.isEmpty()){
            return firstCache
        }

        val posts = arrayParse(postApi.getLastPosts(perPage = perPage, page = page))
        if(perPage == 1 && page == 1){
            firstCache = posts
        }

        return posts
    }

    suspend fun getPostById(id: Int): Post{
        val response: PostRaw = postApi.getPostById(id = id)
        val format = DateTimeFormatter.ofPattern("dd 'de' LLLL 'de' yyyy 'a' HH:mm", Locale("ca-es"))

        return Post(
            id = response.id,
            title = Html.fromHtml(response.title.rendered, Html.FROM_HTML_MODE_LEGACY),
            content = Html.fromHtml(response.content.rendered, Html.FROM_HTML_MODE_LEGACY),
            date = LocalDateTime.parse(response.date).format(format),
            categories = response.categories,
            mediaId = Integer.parseInt(response._links.featuredMedia.get(0).href.substring("https://www.imaginaradio.cat/wp-json/wp/v2/media/".length))
        )
    }

    //Media queries
    suspend fun getPostMedia(id: Int, size: Boolean = false): String{
        if(size){
            return postApi.getPostMedia(id = id).media_details.sizes.thumbnail.source_url
        }
        else {
            return postApi.getPostMedia(id = id).media_details.sizes.full.source_url
        }
    }

    //Parsing posts array response
    fun arrayParse(res: List<PostRaw>): List<Post>{
        val format = DateTimeFormatter.ofPattern("dd 'de' LLLL 'de' yyyy 'a' HH:mm", Locale("ca-es"))
        return res.map{ post ->
            Post(
                id = post.id,
                title = Html.fromHtml(post.title.rendered, Html.FROM_HTML_MODE_LEGACY),
                content = Html.fromHtml(post.content.rendered, Html.FROM_HTML_MODE_LEGACY),
                date = LocalDateTime.parse(post.date).format(format),
                categories = post.categories,
                mediaId = Integer.parseInt(post._links.featuredMedia.get(0).href.substring("https://www.imaginaradio.cat/wp-json/wp/v2/media/".length))
            )
        }
    }

    //Call categories early into a 'cache' for quicker text load
    fun preloadCategories(){
        GlobalScope.launch {
            getLastPosts(1, 1)
            getCategoryPosts(7, 7, 1)
            getCategoryPosts(6486, 7, 1)
            getCategoryPosts(9, 7, 1)
            getCategoryPosts(11, 7, 1)
            getCategoryPosts(687, 7, 1)
            getCategoryPosts(8, 7, 1)
        }
    }
}