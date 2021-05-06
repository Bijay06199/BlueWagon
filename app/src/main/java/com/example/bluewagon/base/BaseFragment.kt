package com.example.bluewagon.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.bluewagon.BR
import com.example.bluewagon.R
import com.example.bluewagon.data.prefs.PreferenceManager
import com.tiper.MaterialSpinner
import org.koin.android.ext.android.inject

abstract class BaseFragment<DATA_BINDING: ViewDataBinding,VIEW_MODEL: BaseViewModel>: Fragment() {
    lateinit var viewDataBinding: DATA_BINDING
    private var baseViewModel: VIEW_MODEL? = null
    val preferenceManager: PreferenceManager by inject()

    lateinit var dialog:Dialog



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        performDataBinding(inflater, container)
        return viewDataBinding.root
    }

    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        this.baseViewModel = baseViewModel ?: getViewModel()
        viewDataBinding.apply {
            setVariable(getBindingVariable(), baseViewModel)
            setLifecycleOwner(viewLifecycleOwner)
            executePendingBindings()
        }
    }

    open fun setupUI(view: View) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard(requireActivity())
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    open fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus

        view?.let {
            val inputMethodManager: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }

    }


    fun setUpDialog(){
        dialog= Dialog(this@BaseFragment.activity!!)
        dialog.setContentView(R.layout.reason_layout)
        dialog.show()
    }


//    fun setImageSrc(imageView: ImageView, imageSrc: String) {
//        with(imageView) {
//            Glide.with(context)
//                .load(imageSrc)
//                .apply(
//                    RequestOptions()
//                        .placeholder(R.drawable.place_holder_icon)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//
//                )
//
//                .into(imageView)
//
//
//        }
//    }



    fun initNewSpinner(options:Int,spinner:com.tiper.MaterialSpinner) {
        val listener by lazy {
            object : MaterialSpinner.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: MaterialSpinner,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.v(
                        "MaterialSpinner",
                        "onItemSelected parent=${parent.id}, position=$position"
                    )
                    //  parent.focusSearch(View.FOCUSABLES_TOUCH_MODE)?.requestFocus()
                }

                override fun onNothingSelected(parent: MaterialSpinner) {
                    Log.v("MaterialSpinner", "onNothingSelected parent=${parent.id}")
                }
            }
        }

        ArrayAdapter.createFromResource(requireContext(), options, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.apply {
                adapter = it
                onItemSelectedListener = listener
            }
        }
    }

    /**
     * @return layout resource id
     */
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

    open fun getBindingVariable(): Int = BR.viewModel


}