package com.raisetech.ecalculo.zorbistore.di


import android.content.Context
import android.content.res.Resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module

val appModule = module{

    single {

        provideResources(get())
    }

    single {

        CoroutineScope(Dispatchers.Main+ Job())
    }


}

fun provideResources(context: Context): Resources =context.resources