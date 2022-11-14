package com.example.tele2demo.di

import com.example.tele2demo.domain.model.UserData
import com.example.tele2demo.presentaion.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    factory { WidgetServiceMonitor() }
    single { UserData() }
    viewModel { LoginViewModel(localRepository = get(), userData = get()) }
    viewModel { BranchViewModel(repository = get()) }
    viewModel { ConfirmDeviceViewModel(repository = get(), localRepository = get()) }
    viewModel { MainViewModel(localRepository = get(), repository = get()) }
    viewModel { InstallmentViewModel(localRepository = get()) }
}