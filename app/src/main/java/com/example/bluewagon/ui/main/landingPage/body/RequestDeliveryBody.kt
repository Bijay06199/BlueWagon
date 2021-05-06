package com.example.bluewagon.ui.main.landingPage.body

data class RequestDeliveryBody(
    val address: String,
    val customerName: String,
    var remarks:String,
    val deliveryTypeName: String,
    val primaryContact: String,
    val receiverAddress: String,
    val receiverName: String,
    val receiverPrimaryContact: String,
    val receiverSecondaryContact: String,
    val secondaryContact: String
)