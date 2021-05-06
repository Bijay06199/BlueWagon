package com.example.bluewagon.ui.main.response

data class UserDTO(
    val contactNumber: String,
    val fyId: Int,
    val id: Int,
    val name: String,
    val role: Role,
    val roleId: Int,
    val roleName: String,
    val status: String,
    val username: String
)