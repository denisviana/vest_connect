package thedantas.vestconnect.presentation.features.register

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.data.model.domain.UserRegister
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.entity.User
import thedantas.vestconnect.domain.interactor.LinkProductToUserInteractor
import thedantas.vestconnect.domain.interactor.RegisterUserInteractor
import thedantas.vestconnect.domain.interactor.UserInfoInteractor
import timber.log.Timber

class RegisterViewModel(
    private val registerUserInteractor: RegisterUserInteractor,
    private val userInfoInteractor: UserInfoInteractor,
    private val linkProductToUserInteractor: LinkProductToUserInteractor
) : BaseViewModel<RegisterState, RegisterCommand>() {

    init {
        newState(RegisterState())
    }

    private var productToLink : Product? = null

    fun setProductToLink(product: Product){
        this.productToLink = product
    }

    fun createUser(user: User) {
        newState { copy(loading = true) }
        viewModelScope.launch {
            try {
                val userResult = registerUserInteractor.invoke(user)
                userInfoInteractor.setUserInfo(userResult)

                productToLink?.let {
                    linkProductToUserInteractor.invoke(userResult, it)
                }

                command.value = RegisterCommand.RegisterSuccessful

            } catch (e: Exception) {
                Timber.e(e)
                command.value = RegisterCommand.RegisterFailed(getFirebaseExceptionResult(e))
            } finally {
                newState { copy(loading = false) }
            }
        }
    }

}

data class RegisterState(
    val loading: Boolean = false
)

sealed class RegisterCommand {
    object RegisterSuccessful : RegisterCommand()
    data class RegisterFailed(val message: Int) : RegisterCommand()
}