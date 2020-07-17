package thedantas.vestconnect.data.mapper

import thedantas.vestconnect.data.model.remote.AuthData
import thedantas.vestconnect.domain.entity.User

fun User.toAuth(password : String) : AuthData =
    AuthData(
        email = this.email,
        password = password
    )


