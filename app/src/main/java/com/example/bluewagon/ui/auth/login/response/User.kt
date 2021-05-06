package com.example.bluewagon.ui.auth.login.response

data class User(
    val fyId: Int,
    val id: Int,
    val name: String,
    val role: Role,
    val ridersAdmin:Boolean,
    val roleId: Int,
    val roleName: String,
    val status: String,
    val username: String
)