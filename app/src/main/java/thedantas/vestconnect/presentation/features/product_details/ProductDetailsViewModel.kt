package thedantas.vestconnect.presentation.features.product_details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.interactor.UserInfoInteractor

class ProductDetailsViewModel(
    val userInfoInteractor: UserInfoInteractor
) : BaseViewModel<ProductDetailsState, ProductDetailsCommand>(){

    init {
        newState(ProductDetailsState())
    }

    fun setProduct(product : Product){
        viewModelScope.launch {
            newState(
                ProductDetailsState(
                    showMoreDetailsContainer = product.owner == userInfoInteractor.getUserUid()
                )
            )
        }
    }

}

data class ProductDetailsState(
    val loading : Boolean = false,
    val showMoreDetailsContainer : Boolean = false
)

sealed class ProductDetailsCommand{

    object LinkProductToAccountSuccessful : ProductDetailsCommand()
    data class LinkProductToAccountFailed(val message : String) : ProductDetailsCommand()

}