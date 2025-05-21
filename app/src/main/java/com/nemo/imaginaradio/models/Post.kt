package com.nemo.imaginaradio.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post (
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val categories: List<Int>,
    val mediaId: String
)

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
    @Json(name = "wp:featuredmedia")
    val featuredMedia: List<FeaturedRaw>
)

@JsonClass(generateAdapter = true)
data class FeaturedRaw(
    val href: String
)