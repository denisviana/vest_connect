package thedantas.vestconnect.presentation.features.onboarding

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.interactor.UserInfoInteractor

class OnboardingViewModel(
    val userInteractor: UserInfoInteractor
) : BaseViewModel<OnBoardingState, OnBoardingCommand>(){


    fun setOnboardingIntroShowed(){
        viewModelScope.launch {
            userInteractor.setOnboardingIntroShowed()
        }
    }

}

data class OnBoardingState(
    val loading : Boolean = false
)

sealed class OnBoardingCommand{}