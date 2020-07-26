package thedantas.vestconnect.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import thedantas.vestconnect.presentation.features.home.HomeViewModel
import thedantas.vestconnect.presentation.features.login.LoginViewModel
import thedantas.vestconnect.presentation.features.nfc_reader.NfcReaderViewModel
import thedantas.vestconnect.presentation.features.pre_login.PreLoginViewModel
import thedantas.vestconnect.presentation.features.product_details.ProductDetailsViewModel
import thedantas.vestconnect.presentation.features.register.RegisterViewModel
import thedantas.vestconnect.presentation.features.splash_screen.SplashViewModel

/**
 * Created by Denis Costa on 28/06/20.
 */

val viewModelModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { PreLoginViewModel(get()) }
    viewModel { LoginViewModel(get(),get(),get()) }
    viewModel { ProductDetailsViewModel(get()) }
    viewModel { NfcReaderViewModel(get()) }

}