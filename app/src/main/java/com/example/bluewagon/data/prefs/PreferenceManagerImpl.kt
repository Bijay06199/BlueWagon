package com.example.bluewagon.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit

open class PreferenceManagerImpl(private val prefs:SharedPreferences):PreferenceManager {


    override fun setCustomerId(customerId: Int?) {
        prefs[IS_CUSTOMER_ID]=customerId
    }

    override fun getCustomerId(): Int? {
        return prefs[IS_CUSTOMER_ID]?:0
    }

    override fun setDeliveryStatus(status: String) {
        prefs[DELIVERY_STATUS]=status
    }

    override fun getDeliveryStatus(): String {
       return prefs[DELIVERY_STATUS]?:""
    }

    override fun setPaymentStatus(status: String) {
        prefs[PAYMENT_STATUS]=status

    }

    override fun getPaymentStatus(): String {
       return prefs[PAYMENT_STATUS]?:""
    }

    override fun setReason(reason: String) {
        prefs[REASON]=reason
    }

    override fun getReason(): String {
       return prefs[REASON]?:"-"
    }

    override fun setHeaderId(id: Int) {
        prefs[IS_HEADER_ID]=id
    }

    override fun getHeaderId(): Int {
       return prefs[IS_HEADER_ID]?:0
    }

    override fun setRemarks(remarks: String) {
        prefs[IS_REMARKS]=remarks
    }

    override fun getRemarks(): String {
       return prefs[IS_REMARKS]?:"-"
    }

    override fun setAdmin(admin: Boolean) {
        prefs[RIDERS_ADMIN]=admin
    }

    override fun getAdmin(): Boolean {
        return prefs[RIDERS_ADMIN]?:false
    }

    override fun setUserName(userName: String) {
        prefs[IS_USERNAME]=userName
    }

    override fun getUserName(): String {
        return prefs[IS_USERNAME]?:""
    }

    override fun setOrganizationId(id: String) {
        prefs[IS_ORGANIZATION_ID]=id

    }

    override fun getOrganizationId(): String {
       return prefs[IS_ORGANIZATION_ID]?:""
    }

    override fun setAccessToken(token: String) {
        prefs[ACCESS_TOKEN]=token
    }

    override fun getAccessToken(): String {
        return prefs[ACCESS_TOKEN]?:""
    }


    override fun setIsLoggedIn(isLoggedIn: Boolean) {
        prefs[IS_LOGGED_IN] = isLoggedIn
    }

    override fun getIsLoggedIn(): Boolean {
        return prefs[IS_LOGGED_IN] ?: false
    }




    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> prefs.edit {
                putString(key, value)
            }
            is Int -> prefs.edit {
                putInt(key, value)
            }
            is Boolean -> prefs.edit {
                putBoolean(key, value)
            }
            is Float -> prefs.edit {
                putFloat(key, value)
            }
            is Long -> prefs.edit {
                putLong(key, value)
            }




            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }


    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?


            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

}