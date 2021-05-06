package com.example.bluewagon.ui.main.home.delivery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.andrognito.flashbar.Flashbar
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseFragment
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.FragmentDeliveryBinding
import com.example.bluewagon.ui.main.MainActivity
import com.example.bluewagon.ui.main.home.delivery.adapter.CompletedAdapter
import com.example.bluewagon.ui.main.home.delivery.adapter.DeliveryAdapter
import com.example.bluewagon.ui.main.home.delivery.response.Data
import com.example.bluewagon.utils.AuthListenerInfo
import com.example.bluewagon.utils.extentions.infoFlashBar
import com.example.bluewagon.utils.extentions.successFlashBar
import com.tiper.MaterialSpinner
import org.koin.androidx.viewmodel.ext.android.viewModel


class DeliveryFragment : BaseFragment<FragmentDeliveryBinding,DeliveryViewModel>(),AuthListenerInfo,DeliveryAdapter.onItemClickListener {

    override fun getLayoutId(): Int =R.layout.fragment_delivery
    override fun getViewModel(): DeliveryViewModel =deliveryViewModel
    private val deliveryViewModel:DeliveryViewModel by viewModel()


    lateinit var deliveryAdapter: DeliveryAdapter
    lateinit var completedAdapter: CompletedAdapter
    var itemlist=ArrayList<Data>()
    var status:String?=null
    var flashbar:Flashbar?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        status=arguments?.getString(Constants.status)

       viewDataBinding.tvStatus.text=status
        initView()
        deliveryAdapter= DeliveryAdapter(this@DeliveryFragment,itemlist,preferenceManager,deliveryViewModel)
        viewDataBinding.rvReport.adapter=deliveryAdapter



        if (status==Constants.pending){
            setUpPending()

        }
        else if (status==Constants.received){
            setUpReceived()

        }
        else if (status==Constants.completed){
            setUpCompleted()

        }

    }

    private fun setUpCompleted() {
        with(viewDataBinding){
            with(deliveryViewModel){
                 getCompletedReport()
                completedAdapter= CompletedAdapter(itemlist)
                rvReport.adapter=completedAdapter

                pendingEvent.observe(viewLifecycleOwner, Observer {
                    pendingResponse?.data?.forEach { i->

                        var address=i.address
                        var billAmount=i.billAmount
                        var customer=i.customerName
                        var chargeAmount=i.deliveryChargeAmount
                        var orderNo=i.orderNo
                        var paymentStatus=i.paymentStatus
                        var contact=i.primaryContact
                        var storeName=i.storeName
                        var orderStatus=i.orderStatus
                        var remarks=i.remarks
                        var secondaryContact=i.secondaryContact
                        var riderRemarks=i.riderRemarks





                        itemlist.add(Data(address,"",0,billAmount,customer,chargeAmount,0,secondaryContact,orderNo
                            ,orderStatus,paymentStatus,contact,address,storeName,remarks,riderRemarks))


                    }
                    completedAdapter.addAll(itemlist)

                })
            }
        }
    }

    private fun setUpReceived() {
        with(viewDataBinding){
            with(deliveryViewModel){
                  getReceivedReport()
                tvRemarks.visibility=View.VISIBLE
                pendingEvent.observe(viewLifecycleOwner, Observer {
                    pendingResponse?.data?.forEach { i->

                        var id=i.id
                        var address=i.address
                        var billAmount=i.billAmount
                        var customer=i.customerName
                        var chargeAmount=i.deliveryChargeAmount
                        var orderNo=i.orderNo
                        var paymentStatus=i.paymentStatus
                        var contact=i.primaryContact
                        var storeName=i.storeName
                        var orderStatus=i.orderStatus
                        var remarks=i.remarks
                        var riderRemarks=i.riderRemarks
                        var secondaryContact=i.secondaryContact

                        preferenceManager.setHeaderId(id)

                        itemlist.add(Data(address,"",0,billAmount,customer,chargeAmount,0,secondaryContact,orderNo
                            ,orderStatus,paymentStatus,contact,address,storeName,remarks,riderRemarks))


                    }
                    deliveryAdapter.addAll(itemlist)


                })
            }
        }
    }

    private fun setUpPending() {
        with(viewDataBinding){
           with(deliveryViewModel){
               getPendingReport()

               tvRemarks.visibility=View.VISIBLE
               pendingEvent.observe(viewLifecycleOwner, Observer {
                   pendingResponse?.data?.forEach { i->
                       var id=i.id
                       var address=i.address
                       var billAmount=i.billAmount
                       var customer=i.customerName
                       var chargeAmount=i.deliveryChargeAmount
                       var orderNo=i.orderNo
                       var paymentStatus=i.paymentStatus
                       var contact=i.primaryContact
                       var storeName=i.storeName
                       var orderStatus=i.orderStatus
                       var remarks=i.remarks
                       var riderRemarks=i.riderRemarks
                       var secondaryContact=i.secondaryContact

                       preferenceManager.setHeaderId(id)

                       itemlist.add(Data(address,"",0,billAmount,customer,chargeAmount,0,secondaryContact,orderNo,orderStatus,paymentStatus,contact,address,storeName,remarks, riderRemarks))


                   }
                   deliveryAdapter.addAll(itemlist)

                  // val paymentStatus=view?.findViewById<MaterialSpinner>(R.id.tv_payment)
                   val deliveryStatus=view?.findViewById<MaterialSpinner>(R.id.tv_order_status)

                   var status=deliveryStatus?.selectedItem

                       preferenceManager.setDeliveryStatus(status.toString())


               })

           }

        }
    }

    private fun initView() {
        with(viewDataBinding){
            topBar.setOnClickListener {
                startActivity(Intent(this@DeliveryFragment.activity,MainActivity::class.java))
            }
            with(deliveryViewModel){
                authListenerInfo=this@DeliveryFragment
            }






        }
    }

    companion object {
        fun start(activity: FragmentActivity, containerId:Int,status:String){
            val fragment= DeliveryFragment()
            var bundle=Bundle()
            bundle.putString(Constants.status,status)
            fragment.arguments=bundle
            activity.supportFragmentManager.beginTransaction()
                .replace(containerId,fragment)
                .commit()
        }
    }


    override fun onSuccess(message: String) {
        flashbar = successFlashBar(message)
        flashbar?.show()


    }

    override fun onStarted() {
//        viewDataBinding.progressBar4.visibility = View.VISIBLE
//        var animation = AnimationUtils.loadAnimation(this.activity, R.anim.rotation_anim)
//        animation.setInterpolator(LinearInterpolator())
//        viewDataBinding.progressBar4.startAnimation(animation)
    }

    override fun onInfo(message: String) {
        flashbar=infoFlashBar(message)
        flashbar?.show()
    }

    override fun onRemarks(position: Int, itemList: Data) {

        setUpDialog()
        var reason=dialog.findViewById<EditText>(R.id.edt_reason)
        var btnOk=dialog.findViewById<Button>(R.id.btn_ok)

        btnOk.setOnClickListener {
            preferenceManager.setRemarks(reason.text.toString())
            deliveryViewModel.updateStatus(preferenceManager.getHeaderId(),preferenceManager.getDeliveryStatus(),preferenceManager.getReason(),preferenceManager.getPaymentStatus(),preferenceManager.getRemarks())
            dialog.dismiss()
        }
    }

    override fun onContactClick(position: Int, itemList: Data) {
        var contactIntent1 = Intent(
            Intent.ACTION_DIAL, Uri.fromParts(
                "tel", itemList.primaryContact, null
            )
        )
        startActivity(contactIntent1)
    }

    override fun onSecContactClick(position: Int, itemList: Data) {

        var contact=Intent(
            Intent.ACTION_DIAL, Uri.fromParts("tel",itemList.secondaryContact,null)
        )
        startActivity(contact)
    }


}