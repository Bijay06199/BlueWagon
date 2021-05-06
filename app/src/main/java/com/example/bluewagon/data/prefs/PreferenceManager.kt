package com.example.bluewagon.data.prefs

interface PreferenceManager {

    fun setIsLoggedIn(isLoggedIn: Boolean)
    fun getIsLoggedIn():Boolean

    fun setCustomerId(customerId:Int?)
    fun getCustomerId():Int?

    fun setDeliveryStatus(status:String)
    fun getDeliveryStatus():String

    fun setPaymentStatus(status: String)
    fun getPaymentStatus():String

    fun setReason(reason:String)
    fun getReason():String

    fun setHeaderId(id:Int)
    fun getHeaderId():Int

    fun setRemarks(remarks:String)
    fun getRemarks():String

    fun setAdmin(admin:Boolean)
    fun getAdmin():Boolean

    fun setUserName(userName:String)
    fun getUserName():String

    fun setOrganizationId(id: String)
    fun getOrganizationId():String

    fun setAccessToken(token:String)
    fun getAccessToken():String



}