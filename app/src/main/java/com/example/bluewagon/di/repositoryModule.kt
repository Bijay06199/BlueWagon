package com.example.bluewagon.di

import com.example.bluewagon.data.api.wagonApiServices
import com.example.bluewagon.data.prefs.PreferenceManager
import com.example.bluewagon.data.prefs.PreferenceManagerImpl
import com.example.bluewagon.data.repo.*
import org.koin.dsl.module
import kotlin.math.sin

val repositoryModule= module {

    single {
        pendingRepository(get(),get())
    }

    single {
        loginRepository(get(),get())
    }

    single {
        receivedRepository(get(),get())
    }

    single {
        completedRepository(get(),get())
    }

    single {
        resetPasswordRepository(get(),get())
    }

    single {
        updateStatusRepository(get(),get())
    }

    single {
        countRepository(get(),get())
    }

    single {
        locationRepository(get(),get())
    }

    single {
        ridersLocationRepository(get(),get())
    }

    single {
        requestDeliveryRepository(get())
    }

    single {
        trackDeliveryRepository(get(),get())
    }

    single {
        trackDeliveryReceiverRepository(get(),get())
    }

}



fun pendingRepository(

    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager

):PendingRepository{
    return PendingRepository(apiServices,preferenceManager)
}


fun receivedRepository(

    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager

):ReceivedRepository{
    return ReceivedRepository(apiServices,preferenceManager)
}


fun completedRepository(

    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager

):CompletedRepository{
    return CompletedRepository(apiServices,preferenceManager)
}

fun loginRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):LoginRepository{
    return LoginRepository(apiServices, preferenceManager)
}

fun resetPasswordRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):ResetPasswordRepository{
    return ResetPasswordRepository(apiServices, preferenceManager)
}

fun updateStatusRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):UpdateStatusRepository{
    return UpdateStatusRepository(apiServices, preferenceManager)
}

fun countRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):CountRepository{
    return CountRepository(apiServices,preferenceManager)
}

fun locationRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):LocationRepository{
    return LocationRepository(apiServices, preferenceManager)
}

fun ridersLocationRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):RidersLocationRepository{
    return RidersLocationRepository(apiServices,preferenceManager)
}

fun requestDeliveryRepository(
    apiServices: wagonApiServices
):RequestDeliveryRepository{
    return RequestDeliveryRepository(apiServices)
}

fun trackDeliveryRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):TrackDeliveryRepository{
    return TrackDeliveryRepository(apiServices, preferenceManager)
}

fun trackDeliveryReceiverRepository(
    apiServices: wagonApiServices,
    preferenceManager: PreferenceManager
):TrackDeliveryReceiverRepository{
    return TrackDeliveryReceiverRepository(apiServices, preferenceManager)
}

