package com.example.bluewagon.ui.main.response

data class Data(
    val lastLocation: String,
    val time:String,
    val lastLocationDate: String,
    val lastLocationDateFormat:String,
    val userDTO: UserDTO
)