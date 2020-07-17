package thedantas.vestconnect.di

import org.koin.dsl.module
import thedantas.vestconnect.data.data_source.FirebaseAuthDataSource
import thedantas.vestconnect.data.data_source.FirebaseDatabaseDataSource
import thedantas.vestconnect.data.data_source.UserInfoLocalDataSource

val dataSourceModule = module {

    factory { UserInfoLocalDataSource(get()) }
    factory { FirebaseAuthDataSource() }
    factory { FirebaseDatabaseDataSource(get()) }
}