package thedantas.vestconnect.domain.repository

import thedantas.vestconnect.data.model.remote.AuthData
import thedantas.vestconnect.domain.entity.User

interface AuthRepository {

    fun signIn(authData: AuthData) : User
    fun signOut(authData: AuthData) : Pair<Boolean,String>
    fun forgotPassword(authData: AuthData): Pair<Boolean,String>
    fun signUp(user: User, password : String) : User

}