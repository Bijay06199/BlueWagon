package com.example.bluewagon.splash

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.example.bluewagon.R
import com.example.bluewagon.ui.auth.login.LoginActivity
import com.example.bluewagon.ui.main.MainActivity
import com.example.bluewagon.ui.main.landingPage.LandingPageActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()


        Handler().postDelayed({
            if (isOnline()) {
//                ObjectAnimator.ofFloat(iv_icons,"translationX",500f).apply {
//                    start()
//                    duration=100
//                }



                iv_icons.animate()
                    .translationX(500f)
                    .setInterpolator(LinearInterpolator()).duration = 1500


                startActivity(Intent(this@SplashActivity, LandingPageActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Please make sure that you have internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }

        }, 2000)


    }

    private fun initView() {

    }

    fun isOnline(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null &&
                cm.activeNetworkInfo!!.isConnectedOrConnecting
    }
}