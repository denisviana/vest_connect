package thedantas.vestconnect.presentation.features.pre_login

import android.location.Location
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.interactor.UserInfoInteractor

class PreLoginViewModel(
    private val userInfoInteractor: UserInfoInteractor
) : BaseViewModel<State,Command>(){

    fun saveUserLocation(location: Location){
        userInfoInteractor.setUserLocation(location)
    }

}

data class State(
    val loading : Boolean = false
)

sealed class Command{
}