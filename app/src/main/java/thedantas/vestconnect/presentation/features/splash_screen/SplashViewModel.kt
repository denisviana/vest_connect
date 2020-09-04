package thedantas.vestconnect.presentation.features.splash_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.interactor.UserAuthInteractor
import thedantas.vestconnect.domain.interactor.UserInfoInteractor

class SplashViewModel(
    private val userAuthInteractor: UserAuthInteractor,
    private val userInfoInteractor: UserInfoInteractor
) : BaseViewModel<SplashState, SplashCommand>() {


    fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {

            try {
                delay(3000)
                command.value =
                    SplashCommand.CheckIfIsLoggedInResult(userAuthInteractor.isUserLogged())
            } catch (e: Exception) {
                command.value = SplashCommand.CheckIfIsLoggedInResult(isLogged = false)
            }

        }
    }

    fun checkIfUserIsOnboardinIntroWasShowed() {
        viewModelScope.launch {

            try {
                delay(3000)
                command.value =
                    SplashCommand.CheckIfOnboardingIntroWasShowed(userInfoInteractor.wasOnboardingIntroShowed())
            } catch (e: Exception) {
                command.value = SplashCommand.CheckIfOnboardingIntroWasShowed(wasShowed = false)
            }

        }
    }

}

data class SplashState(
    val waiting: Boolean = false
)

sealed class SplashCommand {
    data class CheckIfIsLoggedInResult(val isLogged: Boolean) : SplashCommand()
    data class CheckIfOnboardingIntroWasShowed(val wasShowed : Boolean) : SplashCommand()
}