package com.example.bluewagon.ui.main.home.resetPassword

import android.content.Intent
import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.data.repo.ResetPasswordRepository
import com.example.bluewagon.ui.main.MainActivity
import com.example.bluewagon.ui.main.home.resetPassword.response.ResetPasswordResponse
import com.example.bluewagon.utils.AuthListenerInfo
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val resetPasswordRepository: ResetPasswordRepository):BaseViewModel() {

    var currentPassword:String?=null
    var newPassword:String?=null
    var confirmPassword:String?=null
    var authListenerInfo: AuthListenerInfo?=null
    var resetPassword:ResetPasswordResponse?=null


    fun reset(view: View){

        if (currentPassword.isNullOrEmpty()){
            authListenerInfo?.onInfo("Enter current password")
        }
        else if (newPassword.isNullOrEmpty()){
            authListenerInfo?.onInfo("Enter new password")

        }
        else if(newPassword!=confirmPassword){
            authListenerInfo?.onInfo("Password doesnot match")
        }
        else {

            viewModelScope.launch {
                authListenerInfo?.onStarted()
                var response=resetPasswordRepository.resetPassword(currentPassword!!,newPassword!!)

                if (response?.status=="SUCCESS"){
                    Intent(view.context, MainActivity::class.java).also {
                        view.context.startActivity(it)
                    }
                }
            }
        }
    }




}