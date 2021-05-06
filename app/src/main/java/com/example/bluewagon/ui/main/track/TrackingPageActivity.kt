package com.example.bluewagon.ui.main.track

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.andrognito.flashbar.Flashbar
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseActivity
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.ActivityTrackingPageBinding
import com.example.bluewagon.ui.main.MapsActivity
import com.example.bluewagon.ui.main.MapsUserActivity
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.extentions.infoFlashBar
import com.example.bluewagon.utils.extentions.successFlashBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackingPageActivity : BaseActivity<ActivityTrackingPageBinding, TrackingPageViewModel>(),
    AuthListenerInfo {

    override fun getLayoutId(): Int = R.layout.activity_tracking_page
    override fun getViewModel(): TrackingPageViewModel = trackingPageViewModel
    private val trackingPageViewModel: TrackingPageViewModel by viewModel()

    var flashbar: Flashbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var senderName = intent.getStringExtra(Constants.senderName)
        var senderAddress = intent.getStringExtra(Constants.senderAddress)
        var senderPrimaryNo = intent.getStringExtra(Constants.senderPrimaryNo)
        var senderSecondaryNo = intent.getStringExtra(Constants.senderSecondaryNo)
        var refNo = intent.getStringExtra(Constants.refNo)
        var receiverName = intent.getStringExtra(Constants.receiverName)
        var receiverAddress = intent.getStringExtra(Constants.receiverAddress)
        var receiverPrimaryNo = intent.getStringExtra(Constants.receiverPrimaryNo)
        var receiverSecondaryNo = intent.getStringExtra(Constants.receiverSecondaryNo)
        var paymentStatus = intent.getStringExtra(Constants.paymentStatus)
        var lastLocation = intent.getStringExtra(Constants.lastLocation)
        var lastLocationTime = intent.getStringExtra(Constants.lastLocationTime)
        var lastLocationDate = intent.getStringExtra(Constants.lastLocationDate)
        var userName = intent.getStringExtra(Constants.userName)
        var userNumber = intent.getStringExtra(Constants.userContactNo)
        var role=intent.getStringExtra(Constants.role)
        var deliveryNo=intent.getStringExtra(Constants.deliveryNo)
        var name=intent.getStringExtra(Constants.name)
        var contactNo=intent.getStringExtra(Constants.contactNo)
        var remarks=intent.getStringExtra(Constants.remarks)



        with(viewDataBinding) {
            trackingPageViewModel.authListenerInfo = this@TrackingPageActivity
            etNameS.setText(senderName)
            eTAddressS.setText(senderAddress)
            etContactno1S.setText(senderPrimaryNo)
            etContactno2S.setText(senderSecondaryNo)
            etReferenceNo.setText(refNo)
            etNameR.setText(receiverName)
            eTAddressR.setText(receiverAddress)
            etContactno1R.setText(receiverPrimaryNo)
            etContactno2R.setText(receiverSecondaryNo)
            etStatus.setText(paymentStatus)
            edtName.setText(userName)
            etContact.setText(userNumber)
            etRemarks.setText(remarks)



            trackOnMap.setOnClickListener {
                if (lastLocation.isNullOrEmpty() || userName.isNullOrEmpty()) {
                    with(trackingPageViewModel) {
                        authListenerInfo?.onInfo("User Details are Empty")
                    }

                } else {
                    var intent = Intent(this@TrackingPageActivity, MapsUserActivity::class.java)
                    intent.putExtra(Constants.role,role)
                    intent.putExtra(Constants.deliveryNo,deliveryNo)
                    intent.putExtra(Constants.name,name)
                    intent.putExtra(Constants.contactNo,contactNo)
                    intent.putExtra(Constants.lastLocation, lastLocation)
                    intent.putExtra(Constants.lastLocationTime, lastLocationTime)
                    intent.putExtra(Constants.lastLocationDate, lastLocationDate)
                    intent.putExtra(Constants.userName, userName)
                    intent.putExtra(Constants.userContactNo, userNumber)
                    startActivity(intent)
                }


            }

        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onSuccess(message: String) {
        flashbar = successFlashBar(message)
        flashbar?.show()

    }

    override fun onStarted() {

    }

    override fun onInfo(message: String) {
        flashbar = infoFlashBar(message)
        flashbar?.show()
    }
}