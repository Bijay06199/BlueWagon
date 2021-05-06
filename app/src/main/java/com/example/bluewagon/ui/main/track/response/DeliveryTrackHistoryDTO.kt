package com.example.bluewagon.ui.main.track.response

data class DeliveryTrackHistoryDTO(
    val lastLocation: String,
    val lastLocationDate: String,
    val lastLocationDateFormat: String,
    val time: String,
    val userDTO: UserDTO
)