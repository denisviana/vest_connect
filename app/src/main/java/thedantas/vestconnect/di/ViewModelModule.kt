package thedantas.vestconnect.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import thedantas.vestconnect.presentation.features.home.HomeViewModel

/**
 * Created by Denis Costa on 28/06/20.
 */

val viewModelModule = module {

    viewModel { HomeViewModel() }

}