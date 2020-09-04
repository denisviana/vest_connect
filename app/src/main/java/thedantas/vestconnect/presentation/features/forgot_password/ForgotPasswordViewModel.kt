package thedantas.vestconnect.presentation.features.forgot_password

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.interactor.SendForgotPasswordEmailUseCase
import thedantas.vestconnect.presentation.features.login.LoginCommand
import timber.log.Timber

class ForgotPasswordViewModel(
    private val sendForgotPasswordEmailUseCase: SendForgotPasswordEmailUseCase
) : BaseViewModel<ForgotPasswordState, ForgotPasswordCommand>() {

    init {
        newState(ForgotPasswordState())
    }


    fun forgotPassword(email : String){

        newState { copy(loading = true) }

        viewModelScope.launch {
            try {
                sendForgotPasswordEmailUseCase(email)
                command.value = ForgotPasswordCommand.SendEmailPasswordSuccessful
            } catch (e: Exception) {
                Timber.e(e)
                command.value = ForgotPasswordCommand.SendEmailPasswordFailed(getFirebaseExceptionResult(e))
            } finally {
                newState { copy(loading = false) }
            }

        }

    }

}

data class ForgotPasswordState(
    val loading : Boolean = false
)

sealed class ForgotPasswordCommand{
    object SendEmailPasswordSuccessful : ForgotPasswordCommand()
    data class SendEmailPasswordFailed(val message : Int): ForgotPasswordCommand()
}