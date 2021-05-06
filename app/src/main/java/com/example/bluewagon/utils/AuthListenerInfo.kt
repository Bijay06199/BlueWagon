package com.example.bluewagon.utils

interface AuthListenerInfo {

    fun onSuccess(message:String)
    fun onStarted()
    fun onInfo(message: String)
}