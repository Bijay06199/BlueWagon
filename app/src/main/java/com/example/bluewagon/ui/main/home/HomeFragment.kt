package com.example.bluewagon.ui.main.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.bluewagon.R
import com.example.bluewagon.base.BaseFragment
import com.example.bluewagon.constants.Constants
import com.example.bluewagon.databinding.FragmentHomeBinding
import com.example.bluewagon.ui.auth.login.LoginActivity
import com.example.bluewagon.ui.main.home.adapter.HomeAdapter
import com.example.bluewagon.ui.main.home.delivery.DeliveryFragment
import com.example.bluewagon.ui.main.home.delivery.adapter.DeliveryAdapter
import com.example.bluewagon.ui.main.home.delivery.response.Data
import com.example.bluewagon.ui.main.home.resetPassword.ResetPasswordFragment
import com.example.bluewagon.ui.main.home.response.CountResponse
import com.tiper.MaterialSpinner
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(),HomeAdapter.OnItemClickListener {

    override fun getLayoutId(): Int =R.layout.fragment_home
    override fun getViewModel(): HomeViewModel =homeViewModel
    private val homeViewModel:HomeViewModel by viewModel()

    var itemlist=ArrayList<Data>()
    lateinit var homeAdapter: HomeAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getPending()
        getCount()
    }

    private fun getCount() {
        with(viewDataBinding){
            with(homeViewModel){
                getCount()
                var itemList=ArrayList<com.example.bluewagon.ui.main.home.response.Data>()
                countEvent.observe(viewLifecycleOwner, Observer {

                    tvPendingCount.setText(countResponse!!.data[0].totalCount.toString())
                    tvDeliveredCount.setText(countResponse!!.data[1].totalCount.toString())


                })
            }
        }
    }

    private fun getPending() {
        with(viewDataBinding){
            with(homeViewModel){

                homeAdapter= HomeAdapter(this@HomeFragment,itemlist,preferenceManager,homeViewModel)
                rvReport.adapter=homeAdapter

                getPendingReport()


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
                            ,orderStatus,paymentStatus,contact,address,storeName,remarks, riderRemarks))


                    }
                    homeAdapter.addAll(itemlist)




                })
            }
        }
    }

    private fun initView() {
        with(viewDataBinding){
            topBar.setOnClickListener {
                drawer.openDrawer(GravityCompat.START)
            }

            tvUserName.setText(preferenceManager.getUserName())

            sideNavigation.setNavigationItemSelectedListener { menuItem->
                when(menuItem.itemId){
                    R.id.home->{
                        HomeFragment.start(requireActivity(),R.id.main_screen_container)
                           true
                    }
                    R.id.pending->{
                        DeliveryFragment.start(requireActivity(),R.id.main_screen_container,
                            Constants.pending)
                        true
                    }
                    R.id.received->{
                        DeliveryFragment.start(requireActivity(),R.id.main_screen_container,
                            Constants.received)
                        true
                    }
                    R.id.completed->{
                        DeliveryFragment.start(requireActivity(),R.id.main_screen_container,
                            Constants.completed)
                        true
                    }
                    R.id.reset-> {
                         ResetPasswordFragment.start(requireActivity(),R.id.main_screen_container)
                        true
                    }
                    R.id.logout-> {
                        preferenceManager.setIsLoggedIn(false)
                        startActivity(Intent(this@HomeFragment.activity,LoginActivity::class.java))
                        activity?.finish()
                        true
                    }
                    else -> false
                }
           }


        }
    }




    companion object {

        fun start(activity: FragmentActivity,containerId:Int){
            val fragment=HomeFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(containerId,fragment)
                .commit()
        }
    }

    override fun onRemarks(position: Int, itemList: Data) {
        setUpDialog()
        var reason=dialog.findViewById<EditText>(R.id.edt_reason)
        var btnOk=dialog.findViewById<Button>(R.id.btn_ok)

        btnOk.setOnClickListener {
            preferenceManager.setRemarks(reason.text.toString())
            homeViewModel.updateStatus(preferenceManager.getHeaderId(),preferenceManager.getDeliveryStatus(),preferenceManager.getReason(),preferenceManager.getPaymentStatus(),preferenceManager.getRemarks())
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
            Intent.ACTION_DIAL,Uri.fromParts("tel",itemList.secondaryContact,null)
        )
        startActivity(contact)
    }
}