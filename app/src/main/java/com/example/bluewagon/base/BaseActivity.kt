package com.example.bluewagon.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.bluewagon.BR
import com.example.bluewagon.utils.setupUI
import org.koin.android.ext.android.inject

abstract class BaseActivity<DATA_BINDING : ViewDataBinding, VIEW_MODEL : BaseViewModel> : AppCompatActivity() {

        lateinit var viewDataBinding: DATA_BINDING
        private var baseViewModel: VIEW_MODEL? = null
       val preferenceManager: com.example.bluewagon.data.prefs.PreferenceManager by inject()
        lateinit var dialog: Dialog



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            performDataBinding()
            setupUI(viewDataBinding.root)

        }

        private fun performDataBinding() {
            viewDataBinding= DataBindingUtil.setContentView(this,getLayoutId())
            this.baseViewModel=baseViewModel?:getViewModel()
            viewDataBinding.apply {
                setVariable(getBindingVariable(),baseViewModel)
                setLifecycleOwner(this@BaseActivity)
                executePendingBindings()
            }

        }

        /** @return layout resource id*/

        @LayoutRes
        abstract fun getLayoutId(): Int

        /**
         * Override for set view model
         *
         * @return view model instance
         */
        abstract fun getViewModel(): VIEW_MODEL

        /**
         * Override for set binding variable
         *
         * @return variable id
         */

        open fun getBindingVariable(): Int= BR.viewModel






    }
