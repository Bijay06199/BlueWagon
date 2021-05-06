package com.example.bluewagon.utils

import android.app.Application
import com.example.bluewagon.di.apiModule
import com.example.bluewagon.di.repositoryModule
import com.example.bluewagon.di.viewModelModule
import com.raisetech.ecalculo.zorbistore.di.appModule
import com.raisetech.ecalculo.zorbistore.di.persistenceDataModule
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class BlueWagon:Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(applicationContext)
        startKoin {

            androidContext(this@BlueWagon)
            androidFileProperties()
            modules(
                listOf(
                    viewModelModule, apiModule, appModule, repositoryModule, persistenceDataModule
                )
            )
        }
    }
}