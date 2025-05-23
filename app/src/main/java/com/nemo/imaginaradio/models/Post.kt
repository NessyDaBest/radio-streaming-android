package com.nemo.imaginaradio.models

import android.text.Spanned
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Post (
    val id: Int,
    val title: Spanned,
    val content: Spanned,
    val date: String,
    val categories: List<Int>,
    val mediaId: Int
): Serializable

@JsonClass(generateAdapter = true)
data class PostRaw(
    val id: Int,
    val title: TitleRaw,
    val content: ContentRaw,
    val date: String,
    val categories: List<Int>,
    val _links: LinksRaw
)

@JsonClass(generateAdapter = true)
data class TitleRaw(
    val rendered: String
)

@JsonClass(generateAdapter = true)
data class ContentRaw(
    val rendered: String
)

@JsonClass(generateAdapter = true)
data class LinksRaw(
    @Json(name = "wp:featuredmedia")  val featuredMedia: List<FeaturedRaw>
)

@JsonClass(generateAdapter = true)
data class FeaturedRaw(
    val href: String
)