package com.nemo.imaginaradio.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post (
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val categories: List<Int>
)

@JsonClass(generateAdapter = true)
data class PostRaw(
    val id: Int,
    val title: TitleRaw,
    val content: ContentRaw,
    val date: String,
    val categories: List<Int>
)

@JsonClass(generateAdapter = true)
data class TitleRaw(
    val rendered: String
)

@JsonClass(generateAdapter = true)
data class ContentRaw(
    val rendered: String
)