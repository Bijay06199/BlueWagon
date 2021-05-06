package com.example.bluewagon.ui.main.home.delivery.response

data class Data(
    val address: String,
    val assignToName: String,
    val assignedTo: Int,
    val billAmount: Double,
    val customerName: String,
    val deliveryChargeAmount: Double,
    val id: Int,
    val secondaryContact:String?,
    val orderNo: String,
    val orderStatus: String,
    val paymentStatus: String,
    val primaryContact: String,
    val shippingAddress: String,
    val storeName: String,
    val remarks:String?,
    val riderRemarks:String?
)