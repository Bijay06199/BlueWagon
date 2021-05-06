package com.example.bluewagon.di

import com.example.bluewagon.ui.auth.login.LoginViewModel
import com.example.bluewagon.ui.main.MainViewModel
import com.example.bluewagon.ui.main.MapsUserViewModel
import com.example.bluewagon.ui.main.MapsViewModel
import com.example.bluewagon.ui.main.home.HomeViewModel
import com.example.bluewagon.ui.main.home.delivery.DeliveryViewModel
import com.example.bluewagon.ui.main.home.resetPassword.ResetPasswordViewModel
import com.example.bluewagon.ui.main.landingPage.LandingPageViewModel
import com.example.bluewagon.ui.main.landingPage.contact.ContactViewModel
import com.example.bluewagon.ui.main.track.TrackingPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
 viewModel { MainViewModel(get()) }
 viewModel { LoginViewModel(get()) }
 viewModel { HomeViewModel(get(),get(),get()) }
 viewModel { DeliveryViewModel(get(),get(),get(),get()) }
 viewModel { ResetPasswordViewModel(get()) }
 viewModel { MapsViewModel(get()) }
 viewModel { LandingPageViewModel(get(),get(),get()) }
 viewModel { TrackingPageViewModel() }
 viewModel { ContactViewModel() }
 viewModel { MapsUserViewModel(get()) }

}