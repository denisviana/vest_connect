package thedantas.vestconnect.domain.interactor

import thedantas.vestconnect.data.data_source.FirebaseAuthDataSource
import thedantas.vestconnect.data.model.domain.UserAuth

class LoginUserInteractor constructor(
    private val authDataSource: FirebaseAuthDataSource
) {

    suspend operator fun invoke(auth: UserAuth): String =
        authDataSource.loginUser(auth.email, auth.password).user!!.uid
}
