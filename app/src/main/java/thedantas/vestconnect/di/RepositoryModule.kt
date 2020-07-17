package thedantas.vestconnect.di

import org.koin.dsl.module
import thedantas.vestconnect.data.repository.AuthRepositoryImpl
import thedantas.vestconnect.domain.repository.AuthRepository

/**
 * Created by Denis Costa on 28/06/20.
 */

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get()) }

}