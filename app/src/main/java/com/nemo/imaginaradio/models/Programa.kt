package com.nemo.imaginaradio.model

data class Programa(
    val hora: String,
    val titulo: String,
    val descripcion: String? = null,
    val imagenUrl: String
)