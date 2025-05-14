package com.nemo.imaginaradio.models

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {
    //Post queries
    @GET("posts")
    suspend fun getCategoryPosts(
        @Query("_embed") embed: String = "",
        @Query("category") category: Int,
        @Query("per_page") perPage: Int,
        @Query("_fields") fields: String = "id,title,content,date,categories,_links",
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc"
    ): List<PostRaw>

    @GET("posts")
    suspend fun getLastPosts(
        @Query("_embed") embed: String = "",
        @Query("per_page") perPage: Int,
        @Query("_fields") fields: String = "id,title,content,date,categories",
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc"
    ): List<PostRaw>

    @GET("posts/{id}")
    suspend fun getPostById(
        @Path("id") id: Int,
        @Query("_fields") fields: String = "id,title,content,date,categories",
    ): PostRaw

    //Media queries
    @GET("media/{id}")
    suspend fun getPostMedia(
        @Path("id") id: Int,
        @Query("_fields") fields: String = "media_details"
    ): ResponseRaw
}