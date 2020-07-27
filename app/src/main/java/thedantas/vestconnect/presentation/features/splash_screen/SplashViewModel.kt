package thedantas.vestconnect.presentation.features.splash_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.interactor.UserAuthInteractor

class SplashViewModel(
    private val userAuthInteractor: UserAuthInteractor
) : BaseViewModel<SplashState, SplashCommand>() {


    fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {

            try {
                delay(4000)
                command.value =
                    SplashCommand.CheckIfIsLoggedInResult(userAuthInteractor.isUserLogged())
            } catch (e: Exception) {
                command.value = SplashCommand.CheckIfIsLoggedInResult(isLogged = false)
            }

        }
    }

}

data class SplashState(
    val waiting: Boolean = false
)

sealed class SplashCommand {
    data class CheckIfIsLoggedInResult(val isLogged: Boolean) : SplashCommand()
}