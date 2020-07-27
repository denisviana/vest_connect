package thedantas.vestconnect.presentation.features.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.interactor.GetProductListByUserIdInteractor
import thedantas.vestconnect.domain.interactor.UserInfoInteractor
import timber.log.Timber
import java.lang.Exception

/**
 * Created by Denis Costa on 28/06/20.
 */

class HomeViewModel(
    private val getProductListByUserIdInteractor: GetProductListByUserIdInteractor,
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
                val list = getProductListByUserIdInteractor(
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

    @ExperimentalCoroutinesApi
    fun refreshProductList(){

        viewModelScope.launch {
            try {
                val list = getProductListByUserIdInteractor(
                    userId = userInfoInteractor.getUserUid()
                )
                newState(currentState().copy(emptyList = list.isEmpty()))
                command.value = HomeCommand.RefreshProductListSuccessful(products = list)

            }catch (e : Exception){
                Timber.e(e)
                command.value = HomeCommand.GetProductListFailed("Erro inesperado")
            }finally {
                newState(currentState().copy(hasError = true))
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
    data class RefreshProductListSuccessful(val products : MutableList<Product?>) : HomeCommand()

}