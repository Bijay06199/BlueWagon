package com.example.bluewagon.ui.main.landingPage.response

data class Data(
    val address: String,
    val customerName: String,
    val deliveryChargeAmount: Double,
    val deliveryTrackHistoryDTO: DeliveryTrackHistoryDTO,
    val fyId: Int,
    val id: Int,
    val orderNo: Int,
    val paymentStatus: String,
    val primaryContact: String,
    val secondaryContact: String
)