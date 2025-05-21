package com.nemo.imaginaradio.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseRaw(
    val media_details: MediaRaw
)

@JsonClass(generateAdapter = true)
data class MediaRaw(
    val sizes: SizesRaw
)

@JsonClass(generateAdapter = true)
data class SizesRaw(
    val large: FileRaw
)

@JsonClass(generateAdapter = true)
data class FileRaw(
    val source_url: String
)