package com.example.bluewagon.data.repo

import com.example.bluewagon.constants.Constants
import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.ui.main.landingPage.body.RequestDeliveryBody
import com.example.bluewagon.ui.main.landingPage.response.RequestDeliveryResponse
import retrofit2.Response

class RequestDeliveryRepository(var apiServices: wagonApiServices) {

    suspend fun requestDelivery(
        senderName:String,
        senderAddress:String,
        senderPrimaryNo:String,
        senderSecondaryNo:String,
        receiverName:String,
        receiverAddress:String,
        receiverPrimaryNo:String,
        receiverSecondaryNo:String,
        remarks:String,
        deliveryTypeName:String,
    ): Response<RequestDeliveryResponse> {
        val requestData=RequestDeliveryBody(senderAddress,senderName,remarks,deliveryTypeName,senderPrimaryNo,receiverAddress,receiverName,receiverPrimaryNo,receiverSecondaryNo,senderSecondaryNo)
        return apiServices.requestDelivery(Constants.organizationId,requestData)
    }
}