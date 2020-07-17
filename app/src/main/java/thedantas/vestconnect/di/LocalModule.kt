package thedantas.vestconnect.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.dsl.module

val localModule = module {

    single { provideSharedPreferences(get()) }

}

private fun provideSharedPreferences(application: Application) : SharedPreferences{
    return application.getSharedPreferences("vest_connect_prefs", Context.MODE_PRIVATE);
}