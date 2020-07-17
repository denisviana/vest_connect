package thedantas.vestconnect.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val remoteModule = module {

    single { provideFirebaseAuth() }
    single { provideFierabaseDatabase() }

}

private fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

private fun provideFierabaseDatabase() : FirebaseDatabase = Firebase.database