package com.example.bluewagon.ui.main.home.adapter

import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bluewagon.R
import com.example.bluewagon.ui.main.MainActivity
import com.example.bluewagon.ui.main.home.HomeFragment
import com.example.bluewagon.ui.main.home.HomeViewModel
import com.example.bluewagon.ui.main.home.delivery.DeliveryViewModel
import com.example.bluewagon.ui.main.home.delivery.adapter.DeliveryAdapter
import com.example.bluewagon.ui.main.home.delivery.response.Data
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.recyclerview_report.view.*

class HomeAdapter(val listener: OnItemClickListener, var itemList: ArrayList<Data>, val preferenceManager: com.example.bluewagon.data.prefs.PreferenceManager, val homeViewModel: HomeViewModel) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    lateinit var dialog: Dialog
    inner class ViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        var s_no = containerView.tv_sno
        var order_no = containerView.tv_order_no
        var customer = containerView.tv_customer
        var address = containerView.tv_address
        var contact = containerView.tv_contact
        var secContact = containerView.tv_delivery_amt
      //  var payment_status = containerView.tv_payment
        var order_status = containerView.tv_order_status
        var bill_amount=containerView.tv_bill_amt
        var remarks=containerView.tv_remarks
        var riderRemarks=containerView.tv_rider_remarks



        fun setUpDialog(){
            dialog= Dialog(containerView.context)
            dialog.setContentView(R.layout.reason_layout)
            dialog.show()
        }

        fun setUpDialog1(){
            dialog= Dialog(containerView.context)
            dialog.setContentView(R.layout.message_dialog)
            dialog.show()
        }


        fun initNewSpinnerPayment(options: Int, spinner: com.tiper.MaterialSpinner) {
            val listener by lazy {
                object : MaterialSpinner.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: MaterialSpinner,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        preferenceManager.setPaymentStatus(parent.selectedItem.toString())


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

            ArrayAdapter.createFromResource(
                containerView.context,
                options,
                android.R.layout.simple_spinner_item
            ).let {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.apply {
                    adapter = it
                    onItemSelectedListener = listener
                }
            }
        }


        fun initNewSpinnerDelivery(options: Int, spinner: com.tiper.MaterialSpinner) {
            val listener by lazy {
                object : MaterialSpinner.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: MaterialSpinner,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        preferenceManager.setDeliveryStatus(parent.selectedItem.toString())

                        if (parent.selectedItem=="Hold"|| parent.selectedItem=="Cancelled"){

                            setUpDialog()
                            var reason=dialog.findViewById<EditText>(R.id.edt_reason)
                            var btnOk=dialog.findViewById<Button>(R.id.btn_ok)

                            btnOk.setOnClickListener {
                                preferenceManager.setReason(reason.text.toString())
                                homeViewModel.updateStatus(preferenceManager.getHeaderId(),preferenceManager.getDeliveryStatus(),preferenceManager.getReason(),preferenceManager.getPaymentStatus(),preferenceManager.getRemarks())
                                dialog.dismiss()
                            }

                        }
                        else{
                            setUpDialog1()
                            var btnOk=dialog.findViewById<TextView>(R.id.btn_ok)
                            btnOk.setOnClickListener {
                                Intent(containerView.context,MainActivity::class.java).also {
                                    containerView.context.startActivity(it)
                                }
                                homeViewModel.updateStatus(preferenceManager.getHeaderId(),preferenceManager.getDeliveryStatus(),preferenceManager.getReason(),preferenceManager.getPaymentStatus(),preferenceManager.getRemarks())
                                dialog.dismiss()
                            }

                        }
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {

                    }
                }
            }

            ArrayAdapter.createFromResource(
                containerView.context,
                options,
                android.R.layout.simple_spinner_item
            ).let {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.apply {
                    adapter = it
                    onItemSelectedListener = listener
                }
            }
        }





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_report, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customer.text = itemList[position].customerName
        holder.address.text = itemList[position].address
        holder.contact.text = itemList[position].primaryContact
        holder.secContact.text = itemList[position].secondaryContact
       // holder.payment_status.hint = itemList[position].paymentStatus
        holder.order_status.hint = itemList[position].orderStatus
        holder.order_no.text = itemList[position].orderNo
        holder.s_no.text = position.plus(1).toString()
        holder.bill_amount.text=itemList[position].billAmount.toString()
        holder.riderRemarks.text=itemList[position].riderRemarks

        holder.riderRemarks.setOnClickListener {
            listener.onRemarks(holder.adapterPosition,itemList[holder.adapterPosition])
        }
        holder.contact.setOnClickListener {
            listener.onContactClick(holder.adapterPosition,itemList[holder.adapterPosition])
        }
        holder.secContact.setOnClickListener {
            listener.onSecContactClick(holder.adapterPosition,itemList[holder.adapterPosition])
        }

        preferenceManager.setPaymentStatus(itemList[position].paymentStatus)
        preferenceManager.setDeliveryStatus(itemList[position].orderStatus)

        holder.riderRemarks.text=preferenceManager.getRemarks()

     //   holder.initNewSpinnerPayment(R.array.payment_status, holder.payment_status)
        holder.initNewSpinnerDelivery(R.array.delivery_status, holder.order_status)


    }

    override fun getItemCount(): Int = itemList.size

    fun addAll(itemList: ArrayList<Data>) {
        this.itemList = itemList
        notifyDataSetChanged()


    }

    interface OnItemClickListener{
        fun onRemarks(position: Int,itemList: Data)
        fun onContactClick(position: Int,itemList: Data)
        fun onSecContactClick(position: Int,itemList: Data)
    }


}