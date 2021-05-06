package com.example.bluewagon.ui.main.landingPage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.andrognito.flashbar.Flashbar
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseActivity
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.ActivityLandingPageBinding
import com.example.bluewagon.ui.auth.login.LoginActivity
import com.example.bluewagon.ui.main.landingPage.contact.ContactActivity
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.extentions.infoFlashBar
import com.example.bluewagon.utils.extentions.successFlashBar
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingPageActivity : BaseActivity<ActivityLandingPageBinding,LandingPageViewModel>(),AuthListenerInfo {
    override fun getLayoutId(): Int =R.layout.activity_landing_page

    override fun getViewModel(): LandingPageViewModel =landingPageViewModel
    private val landingPageViewModel:LandingPageViewModel by viewModel()

    var flashbar:Flashbar?=null
    var selectedItem:Any?=null
    var view:View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setUpObservers()
    }

    private fun setUpObservers() {
        with(landingPageViewModel){
            trackEvent.observe(this@LandingPageActivity, Observer {

                when (selectedItem) {
                    "Sender" -> {
                        trackDeliveryBySender(this@LandingPageActivity)

                      }
                    "Receiver" -> {
                        trackDeliveryByReceiver(this@LandingPageActivity)

                    }
                    else -> {
                        authListenerInfo?.onInfo("Select sender or receiver")
                    }
                }

            })

        }
    }

    private fun initView() {
        with(viewDataBinding){
            viewDataBinding.progressBar4.visibility=View.GONE

            landingPageViewModel.authListenerInfo=this@LandingPageActivity
            topBar.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            ivLink.setOnClickListener {
                val url="https://raisetech.com.np/"
                val  i=Intent(Intent.ACTION_VIEW)
                i.data= Uri.parse(url)
                startActivity(i)
            }

            sideNavigation.setNavigationItemSelectedListener { menuItem->
                when(menuItem.itemId){
                    R.id.admin_login->{
                        startActivity(Intent(this@LandingPageActivity,LoginActivity::class.java))
                        drawerLayout.close()
                        true
                    }
                    R.id.about_us->{
                        var intent=Intent(this@LandingPageActivity,ContactActivity::class.java)
                        intent.putExtra(Constants.admin,"AboutUs")
                        startActivity(intent)
                        drawerLayout.close()
                        true
                    }
                    R.id.contact_us->{
                        var intent=Intent(this@LandingPageActivity,ContactActivity::class.java)
                        intent.putExtra(Constants.admin,"ContactUs")
                        startActivity(intent)
                        drawerLayout.close()
                        true

                    }
                    else ->false
                }
             }

            initNewSpinner(R.array.party,spinnerParty)
        }


    }



    fun initNewSpinner(options:Int,spinner:com.tiper.MaterialSpinner) {
        val listener by lazy {
            object : MaterialSpinner.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: MaterialSpinner,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                  selectedItem=parent.selectedItem


                  }

                override fun onNothingSelected(parent: MaterialSpinner) {

                }
            }
        }

        ArrayAdapter.createFromResource(this, options, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.apply {
                adapter = it
                onItemSelectedListener = listener
            }
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
        finishAffinity()
    }
}