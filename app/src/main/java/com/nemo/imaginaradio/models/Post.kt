package com.nemo.imaginaradio.models

import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class Post (
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val categories: List<Int>
)