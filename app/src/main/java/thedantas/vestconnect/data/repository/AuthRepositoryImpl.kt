package thedantas.vestconnect.data.repository

import thedantas.vestconnect.data.data_source.FirebaseAuthDataSource
import thedantas.vestconnect.data.model.remote.AuthData
import thedantas.vestconnect.domain.entity.User
import thedantas.vestconnect.domain.repository.AuthRepository

class AuthRepositoryImpl(
    val firebaseAuthDataSource: FirebaseAuthDataSource
): AuthRepository {

    override fun signIn(authData: AuthData): User {
        TODO("Not yet implemented")
    }

    override fun signOut(authData: AuthData): Pair<Boolean, String> {
        TODO("Not yet implemented")
    }

    override fun forgotPassword(authData: AuthData): Pair<Boolean, String> {
        TODO("Not yet implemented")
    }

    override fun signUp(user: User, password: String): User {
        TODO("Not yet implemented")
    }

}