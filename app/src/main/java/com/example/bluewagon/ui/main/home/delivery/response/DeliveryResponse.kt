package com.example.bluewagon.ui.main.home.delivery.response

data class DeliveryResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)