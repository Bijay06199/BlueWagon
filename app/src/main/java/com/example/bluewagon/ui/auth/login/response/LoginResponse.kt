package com.example.bluewagon.ui.auth.login.response

data class LoginResponse(
    val access_token: String,
    val endDate: String,
    val expiry: Long,
    val fyId: String,
    val fyName: String,
    val isEnglishDateEnabled: Boolean,
    val nepaliNewDate: String,
    val organisationId: String,
    val packageModule: String,
    val startDate: String,
    val status: String,
    val transactionDate: String,
    val user: User,
    val warehouseId: WarehouseId
)