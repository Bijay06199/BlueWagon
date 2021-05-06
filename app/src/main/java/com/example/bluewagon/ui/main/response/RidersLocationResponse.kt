package com.example.bluewagon.ui.main.response

data class RidersLocationResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)