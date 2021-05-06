package com.example.bluewagon.ui.main.landingPage.contact

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseActivity
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.ActivityContactBinding
import com.example.bluewagon.ui.main.home.HomeFragment
import com.example.bluewagon.ui.main.landingPage.LandingPageActivity
import com.example.bluewagon.utils.GPSTracker
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactActivity : BaseActivity<ActivityContactBinding,ContactViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_contact
    override fun getViewModel(): ContactViewModel = contactViewModel
    private val contactViewModel: ContactViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var title=intent.getStringExtra(Constants.admin)
        if (title=="ContactUs"){
            viewDataBinding.contactView.visibility= View.VISIBLE
            viewDataBinding.lLAbout.visibility=View.GONE
        }
        else if (title=="AboutUs"){
            viewDataBinding.contactView.visibility= View.GONE
            viewDataBinding.lLAbout.visibility=View.VISIBLE
        }
        initView()
        setUpClickListeners()


    }

    private fun setUpClickListeners() {
        with(viewDataBinding) {

            tVContact.setOnClickListener {
                var contactIntent = Intent(
                    Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", this@ContactActivity?.getString(R.string.contact1), null
                    )
                )
                startActivity(contactIntent)
            }

            tVContact1.setOnClickListener {
                var contactIntent1 = Intent(
                    Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", this@ContactActivity.getString(R.string.contact2), null
                    )
                )
                startActivity(contactIntent1)
            }

            tVMail.setOnClickListener {
                val recepientEmail =
                    this@ContactActivity.getString(R.string.mail) // either set to destination email or leave empty
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.setData(Uri.parse("mailto:" + recepientEmail))
                startActivity(intent)

            }

            tVLocation.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        this@ContactActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                       this@ContactActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    showMap()
                } else {
                    ActivityCompat.requestPermissions(
                        this@ContactActivity,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        1
                    )
                }

            }

            iVInstagram.setOnClickListener {
               // getInstagramAccount()

            }

            iVFacebook.setOnClickListener {

               // startActivity(getOpenFacebookIntent())
            }


        }
    }

    private fun showMap() {
        val gps = GPSTracker(this)
        val latitude: Double = gps.getLatitude()
        val longitude: Double = gps.getLongitude()

        val destinationLatitude = 27.702665439482338
        val destinationLongitude =85.32154349800707
        val uri =
            "http://maps.google.com/maps?saddr=$latitude,$longitude&daddr=$destinationLatitude,$destinationLongitude"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)

    }

    private fun getOpenFacebookIntent(): Intent? {
        return try {
            this.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/zorbistore")
            )
        } catch (e: Exception) {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/zorbistore")
            )
        }
    }

    private fun getInstagramAccount() {
        val uri = Uri.parse("https://www.instagram.com/zorbistore")
        val likeIng = Intent(Intent.ACTION_VIEW, uri)

        likeIng.setPackage("com.instagram.android")

        try {
            startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/zorbistore")
                )
            )
        }
    }


    private fun initView() {
        with(viewDataBinding) {

            linkClick.setOnClickListener {
                val url = "https://raisetech.com.np/"

                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

            ivBack.setOnClickListener {
               startActivity(Intent(this@ContactActivity,LandingPageActivity::class.java))
                finish()
            }
        }
    }
}