package com.example.bluewagon.ui.main.home.response

data class CountResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)