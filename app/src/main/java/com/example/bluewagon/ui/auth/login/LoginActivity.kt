package com.example.bluewagon.ui.auth.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.preference.PreferenceManager
import com.andrognito.flashbar.Flashbar
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseActivity
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.ActivityLoginBinding
import com.example.bluewagon.ecalculo.activities.startup.DashboardActivity
import com.example.bluewagon.ecalculo.activities.startup.LoginSplashActivity
import com.example.bluewagon.ecalculo.utilities.AppTexts
import com.example.bluewagon.ui.main.MainActivity
import com.example.bluewagon.ui.main.MapsActivity
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.extentions.infoFlashBar
import com.example.bluewagon.utils.extentions.successFlashBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>(),AuthListenerInfo {

    override fun getLayoutId(): Int =R.layout.activity_login
    override fun getViewModel(): LoginViewModel =loginViewModel
    private val loginViewModel:LoginViewModel by viewModel()

   var flashbar:Flashbar?=null
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setUpObservers()

    }



    private fun initView() {
        with(viewDataBinding){
            progressBar4.visibility=View.GONE
            preferences=PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
            if (preferenceManager.getIsLoggedIn()==true){
                if (preferenceManager.getAdmin()==true){
                    var intent=Intent(this@LoginActivity,MapsActivity::class.java)
                    startActivity(intent)

                }
                else if (preferenceManager.getAdmin()==false){
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))

                    }
            }

            btnAdmin.setOnClickListener {
                if (preferences.getBoolean(AppTexts.loggedin,false)){
                    startActivity(Intent(this@LoginActivity,DashboardActivity::class.java))
                }
                else{
                    startActivity(Intent(this@LoginActivity,LoginSplashActivity::class.java))
                }
            }
        }
    }

    private fun setUpObservers() {

        with(loginViewModel){
            authListenerInfo=this@LoginActivity

        }
    }

    override fun onSuccess(message: String) {
        flashbar = successFlashBar(message)
        flashbar?.show()
        viewDataBinding.progressBar4.visibility = View.GONE

    }

    override fun onStarted() {
        viewDataBinding.progressBar4.visibility = View.VISIBLE
        var animation = AnimationUtils.loadAnimation(this, R.anim.rotation_anim)
        animation.setInterpolator(LinearInterpolator())
        viewDataBinding.progressBar4.startAnimation(animation)
    }

    override fun onInfo(message: String) {
       flashbar=infoFlashBar(message)
        flashbar?.show()
        viewDataBinding.progressBar4.visibility = View.GONE
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
