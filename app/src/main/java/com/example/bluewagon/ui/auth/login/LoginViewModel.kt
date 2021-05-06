package com.example.bluewagon.ui.auth.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.bluewagon.base.BaseViewModel
import com.example.bluewagon.data.repo.LocationRepository
import com.example.bluewagon.data.repo.LoginRepository
import com.example.bluewagon.ui.main.MainActivity
import com.example.bluewagon.ui.main.MapsActivity
import com.example.bluewagon.utils.ApiException
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.NoInternetException
import com.example.bluewagon.utils.SingleLiveEvent
import com.example.bluewagon.utils.extentions.isValidEmail
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository):BaseViewModel() {
    var email:String?=null
    var password:String?=null
    var locationEvent=SingleLiveEvent<Unit>()
    var authListenerInfo:AuthListenerInfo?=null




    fun login(view: View){

        if (email.isNullOrEmpty()){
            authListenerInfo?.onInfo("Please enter email")
        }
        else if (password.isNullOrEmpty()){
            authListenerInfo?.onInfo("Please enter password")

        }
        else {

            viewModelScope.launch {
                authListenerInfo?.onStarted()
                try {
                    var response=loginRepository.login(email!!,password!!)
                    if (response.status=="SUCCESS"){
                        if (response.user.ridersAdmin==true){
                            loginRepository.preferenceManager.setAdmin(true)
                            Intent(view.context,MapsActivity::class.java).also {
                                view.context.startActivity(it)
                            }
                        }
                        else{
                            loginRepository.preferenceManager.setAdmin(false)
                            loginRepository.preferenceManager.setUserName(response.user.name)
                            Intent(view.context,MainActivity::class.java).also {
                                view.context.startActivity(it)

                            }
                        }
                        loginRepository.preferenceManager.setCustomerId(response.user.id)
                        loginRepository.preferenceManager.setAccessToken(response.access_token)
                        loginRepository.preferenceManager.setOrganizationId(response.organisationId)
                        loginRepository.preferenceManager.setIsLoggedIn(true)

                    }
                    else{
                        authListenerInfo?.onInfo(response.status)
                    }
                }catch (e:NoInternetException){authListenerInfo?.onInfo(e.message!!)}
                 catch (e:ApiException){authListenerInfo?.onInfo("Enter valid email or password")}
            }



        }

    }



}