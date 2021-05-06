package com.example.bluewagon.ui.main.track.response

data class Data(
    val address: String,
    val assignedTo: Int,
    val customerName: String,
    val deliveryChargeAmount: Double,
    val deliveryTrackHistoryDTO: DeliveryTrackHistoryDTO,
    val fyId: Int,
    val id: Int,
    val orderNo: Int,
    val paymentStatus: String,
    val orderStatus:String,
    val primaryContact: String,
    val receiverAddress: String,
    val receiverName: String,
    val receiverPrimaryContact: String,
    val receiverSecondaryContact: String,
    val refNo: String,
    val secondaryContact: String,
    val remarks:String
)