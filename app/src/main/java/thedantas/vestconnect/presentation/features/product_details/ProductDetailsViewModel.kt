package thedantas.vestconnect.presentation.features.product_details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.interactor.GetUserInteractor
import thedantas.vestconnect.domain.interactor.LinkProductToUserInteractor
import thedantas.vestconnect.domain.interactor.UserAuthInteractor
import thedantas.vestconnect.domain.interactor.UserInfoInteractor
import timber.log.Timber
import java.lang.Exception

class ProductDetailsViewModel(
    private val userInfoInteractor: UserInfoInteractor,
    private val linkProductToUserInteractor: LinkProductToUserInteractor,
    private val getUserInteractor: GetUserInteractor,
    private val userAuthInteractor: UserAuthInteractor,
    private val authInteractor: UserAuthInteractor
) : BaseViewModel<ProductDetailsState, ProductDetailsCommand>(){

    init {
        newState(ProductDetailsState())
    }

    fun setProduct(product : Product){
        viewModelScope.launch {
            newState(
                ProductDetailsState(
                    showMoreDetailsContainer = authInteractor.isUserLogged() && product.owner == userInfoInteractor.getUserUid(),
                    showLinkButton =
                    (authInteractor.isUserLogged() && product.owner.isEmpty()) ||
                            (!userAuthInteractor.isUserLogged() && product.owner.isEmpty())
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    fun linkProductToUser(product : Product){

        viewModelScope.launch {

            try {

                if(!userAuthInteractor.isUserLogged())
                    command.value = ProductDetailsCommand.UserIsNotLoggedIn(product)
                else{

                    newState(ProductDetailsState(loading = true))

                    val user = getUserInteractor(
                        uid = userInfoInteractor.getUserUid()
                    )
                    val result = linkProductToUserInteractor.invoke(user!!, product)
                    command.value = ProductDetailsCommand.LinkProductToUserSuccessful(result)

                    newState(ProductDetailsState(loading = false, showMoreDetailsContainer = true, showLinkButton = false))

                }

            }catch (e : Exception){
                Timber.e(e)
                newState(ProductDetailsState(loading = false))
                command.value = ProductDetailsCommand.LinkProductToUserFailed(message = "Falha ao vincular produto")
            }

        }

    }

}

data class ProductDetailsState(
    val loading : Boolean = false,
    val showMoreDetailsContainer : Boolean = false,
    val showLinkButton : Boolean = false
)

sealed class ProductDetailsCommand{

    data class LinkProductToUserSuccessful(val product : Product) : ProductDetailsCommand()
    data class LinkProductToUserFailed(val message : String) : ProductDetailsCommand()
    data class UserIsNotLoggedIn(val product : Product) : ProductDetailsCommand()

}