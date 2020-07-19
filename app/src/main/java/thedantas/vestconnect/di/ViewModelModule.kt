package thedantas.vestconnect.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import thedantas.vestconnect.presentation.features.home.HomeViewModel
import thedantas.vestconnect.presentation.features.pre_login.PreLoginViewModel
import thedantas.vestconnect.presentation.features.register.RegisterViewModel
import thedantas.vestconnect.presentation.features.splash_screen.SplashViewModel

/**
 * Created by Denis Costa on 28/06/20.
 */

val viewModelModule = module {

    viewModel { HomeViewModel() }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { PreLoginViewModel(get()) }

}