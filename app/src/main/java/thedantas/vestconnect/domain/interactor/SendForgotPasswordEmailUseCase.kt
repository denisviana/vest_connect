package thedantas.vestconnect.domain.interactor

import thedantas.vestconnect.data.data_source.FirebaseAuthDataSource

class SendForgotPasswordEmailUseCase(
    private val firebaseAuthDataSource: FirebaseAuthDataSource
) {

    suspend operator fun invoke(email: String) =
        firebaseAuthDataSource.forgotPassword(email)

}