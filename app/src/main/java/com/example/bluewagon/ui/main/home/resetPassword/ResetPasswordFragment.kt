package com.example.bluewagon.ui.main.home.resetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.fragment.app.FragmentActivity
import com.andrognito.flashbar.Flashbar
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseFragment
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.FragmentResetPasswordBinding
import com.example.bluewagon.ui.main.home.delivery.DeliveryFragment
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.extentions.infoFlashBar
import com.example.bluewagon.utils.extentions.successFlashBar
import org.koin.androidx.viewmodel.ext.android.viewModel


class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding,ResetPasswordViewModel>(),AuthListenerInfo {

    override fun getLayoutId(): Int =R.layout.fragment_reset_password
    override fun getViewModel(): ResetPasswordViewModel =resetPasswordViewModel
    private val resetPasswordViewModel:ResetPasswordViewModel by viewModel()

    var flashbar: Flashbar?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(viewDataBinding){
            resetPasswordViewModel.authListenerInfo=this@ResetPasswordFragment
            progressBar4.visibility=View.GONE
        }
    }

    companion object {
        fun start(activity: FragmentActivity, containerId:Int){
            val fragment= ResetPasswordFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(containerId,fragment)
                .commit()
        }
    }


    override fun onSuccess(message: String) {
        flashbar = successFlashBar(message)
        flashbar?.show()
        viewDataBinding.progressBar4.visibility = View.GONE

    }

    override fun onStarted() {
        viewDataBinding.progressBar4.visibility = View.VISIBLE
        var animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotation_anim)
        animation.setInterpolator(LinearInterpolator())
        viewDataBinding.progressBar4.startAnimation(animation)

    }

    override fun onInfo(message: String) {
        flashbar = infoFlashBar(message)
        flashbar?.show()
        viewDataBinding.progressBar4.visibility = View.GONE

    }





}