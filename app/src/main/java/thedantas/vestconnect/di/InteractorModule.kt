package thedantas.vestconnect.di

import org.koin.dsl.module
import thedantas.vestconnect.domain.interactor.*

val interactorModule = module {

    single { RegisterUserInteractor(get(), get(), get()) }
    single { UserInfoInteractor(get()) }
    single { UserAuthInteractor(get()) }
    single { GetUserInteractor(get())}
    single { LoginUserInteractor(get())}
    single { GetProductListByUserId(get()) }
    single { GetProductByNfcTagId(get()) }

}