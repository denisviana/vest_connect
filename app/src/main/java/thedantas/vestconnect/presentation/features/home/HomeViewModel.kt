package thedantas.vestconnect.presentation.features.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.entity.User
import thedantas.vestconnect.domain.interactor.GetProductListByUserId
import thedantas.vestconnect.domain.interactor.UserInfoInteractor
import timber.log.Timber
import java.lang.Exception

/**
 * Created by Denis Costa on 28/06/20.
 */

class HomeViewModel(
    private val getProductListByUserId: GetProductListByUserId,
    private val userInfoInteractor: UserInfoInteractor
) : BaseViewModel<HomeState, HomeCommand>(){

    init {

        viewModelScope.launch {
            newState(HomeState(userName =  userInfoInteractor.getUsername()))
        }

    }

    @ExperimentalCoroutinesApi
    fun getProductList(){

        viewModelScope.launch {
            try {
                val list = getProductListByUserId(
                    userId = userInfoInteractor.getUserUid()
                )
                newState(currentState().copy(loading = false, emptyList = list.isEmpty()))
                command.value = HomeCommand.GetProductListSuccessful(products = list)

            }catch (e : Exception){
                Timber.e(e)
                command.value = HomeCommand.GetProductListFailed("Erro inesperado")
            }finally {
                newState(currentState().copy(loading = false, hasError = true))
            }

        }

    }

}

data class HomeState(
    val loading : Boolean = true,
    val hasError : Boolean = false,
    val emptyList : Boolean = false,
    val userName : String = ""
)

sealed class HomeCommand{
    data class GetProductListSuccessful(val products : MutableList<Product?>) : HomeCommand()
    data class GetProductListFailed(val message : String) : HomeCommand()
}