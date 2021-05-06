package com.example.bluewagon.data.api

import com.example.bluewagon.ui.auth.login.body.LocationBody
import com.example.bluewagon.ui.auth.login.body.LoginBody
import com.example.bluewagon.ui.auth.login.response.LocationResponse
import com.example.bluewagon.ui.auth.login.response.LoginResponse
import com.example.bluewagon.ui.main.home.delivery.response.DeliveryResponse
import com.example.bluewagon.ui.main.home.resetPassword.body.ResetPasswordBody
import com.example.bluewagon.ui.main.home.resetPassword.response.ResetPasswordResponse
import com.example.bluewagon.ui.main.home.response.CountResponse
import com.example.bluewagon.ui.main.landingPage.body.RequestDeliveryBody
import com.example.bluewagon.ui.main.landingPage.response.RequestDeliveryResponse
import com.example.bluewagon.ui.main.response.RidersLocationResponse
import com.example.bluewagon.ui.main.track.response.TrackDeliveryResponse
import retrofit2.Response
import retrofit2.http.*

interface wagonApiServices {

    @GET("delivery/pendingDeliveryListByUserId/{id}")
    suspend fun getPending(
        @Path(value="id")id:Int
    ): Response<DeliveryResponse>

    @GET("/delivery/receivedByRiderFromVendorListByUserId/{id}")
    suspend fun getReceived(
        @Path(value="id")id:Int
    ): Response<DeliveryResponse>


    @GET("delivery/deliveredListByUserId/{id}")
    suspend fun getCompleted(
        @Path(value="id")id:Int
    ): Response<DeliveryResponse>


    @POST("auth")
    suspend fun login(
        @Body params: LoginBody
    ): Response<LoginResponse>

    @PUT("/users/resetPassword/{id}")
    suspend fun resetPassword(
        @Path(value="id")id: Int,
        @Body params :ResetPasswordBody
    ):Response<ResetPasswordResponse>


    @PUT("delivery/updateStatusByRider/{id}/{deliveryStatus}/{reason}/{paymentStatus}/{remarks}")
    suspend fun updateStatus(
        @Path(value="id")id:Int,
        @Path(value = "deliveryStatus")deliveryStatus:String,
        @Path(value = "reason")reason:String,
        @Path(value="paymentStatus")paymentStatus:String,
        @Path(value = "remarks")remarks:String
    ):Response<ResetPasswordResponse>


    @GET("delivery/dashboardSummary/{id}")
    suspend fun getCount(
        @Path(value = "id")id: Int
    ):Response<CountResponse>

    @POST("delivery/postLastLocationByUser/")
    suspend fun getLocation(
        @Body params:LocationBody
    ):Response<LocationResponse>


    @GET("delivery/getRidersLastLocation/{organisationId}")
    suspend fun getRidersLocation(
        @Path(value="organisationId")organisationId: String
    ):Response<RidersLocationResponse>

    @POST("/delivery/saveDeliveryRequest/{organisationId}")
    suspend fun requestDelivery(
        @Path(value="organisationId")organisationId: Int,
        @Body params:RequestDeliveryBody
    ):Response<RequestDeliveryResponse>

    @GET("delivery/tackDeliveryNoBySender/{deliverNo}/{name}/{contactNumber}/{organisationId}")
    suspend fun trackDeliveryBySender(
        @Path(value="deliverNo")deliverNo:String,
        @Path(value = "name")name:String,
        @Path(value="contactNumber")contactNumber:String,
        @Path(value="organisationId")organisationId: Int
    ):Response<TrackDeliveryResponse>

    @GET("delivery/tackDeliveryNoByReceiver/{deliverNo}/{name}/{contactNumber}/{organisationId}")
    suspend fun trackDeliveryByReceiver(
        @Path(value="deliverNo")deliverNo:String,
        @Path(value = "name")name:String,
        @Path(value="contactNumber")contactNumber:String,
        @Path(value="organisationId")organisationId: Int
    ):Response<TrackDeliveryResponse>

}