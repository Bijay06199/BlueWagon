package com.example.bluewagon.ui.main.home.delivery.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.bluewagon.R
import com.example.bluewagon.ui.main.home.delivery.DeliveryViewModel
import com.example.bluewagon.ui.main.home.delivery.response.Data
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.recyclerview_completed.view.*

class CompletedAdapter( var itemList: ArrayList<Data>) :
    RecyclerView.Adapter<CompletedAdapter.ViewHolder>() {


    lateinit var dialog: Dialog
    inner class ViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        var s_no = containerView.tv_sno
        var order_no = containerView.tv_order_no
        var customer = containerView.tv_customer
        var address = containerView.tv_address
        var contact = containerView.tv_contact
        var secContact = containerView.tv_delivery_amt
   //     var payment_status = containerView.tv_payment
        var order_status = containerView.tv_order_status
        var bill_amount=containerView.tv_bill_amt
        var remarks=containerView.tv_remarks
        var riderRemarks=containerView.tv_rider_remarks


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_completed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customer.text = itemList[position].customerName
        holder.address.text = itemList[position].address
        holder.contact.text = itemList[position].primaryContact
        holder.secContact.text = itemList[position].secondaryContact
     //   holder.payment_status.text = itemList[position].paymentStatus
        holder.order_status.text = itemList[position].orderStatus
        holder.order_no.text = itemList[position].orderNo
        holder.bill_amount.text=itemList[position].billAmount.toString()
        holder.remarks.text=itemList[position].remarks
        holder.riderRemarks.text=itemList[position].riderRemarks
        holder.s_no.text = position.plus(1).toString()


    }

    override fun getItemCount(): Int = itemList.size

    fun addAll(itemList: ArrayList<Data>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }



}