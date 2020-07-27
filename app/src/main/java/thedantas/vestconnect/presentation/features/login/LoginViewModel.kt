package thedantas.vestconnect.presentation.features.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.data.model.domain.UserAuth
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.interactor.GetUserInteractor
import thedantas.vestconnect.domain.interactor.LinkProductToUserInteractor
import thedantas.vestconnect.domain.interactor.LoginUserInteractor
import thedantas.vestconnect.domain.interactor.UserInfoInteractor
import timber.log.Timber

/**
 * Created by Denis Costa on 28/06/20.
 */
class LoginViewModel(
    private val loginUserInteractor: LoginUserInteractor,
    private val userInfoInteractor: UserInfoInteractor,
    private val getUserInteractor: GetUserInteractor,
    private val linkProductToUserInteractor: LinkProductToUserInteractor
) : BaseViewModel<LoginState,LoginCommand>() {

    init {
        newState(LoginState())
    }

    private var productToLink : Product? = null

    fun setProductToLink(product: Product){
        this.productToLink = product
    }

    @ExperimentalCoroutinesApi
    fun login(auth: UserAuth) {
        newState { copy(loading = true) }
        viewModelScope.launch {
            try {
                val uid = loginUserInteractor.invoke(auth)
                val user = getUserInteractor.invoke(uid)
                user?.let {

                    userInfoInteractor.setUserInfo(it)

                    productToLink?.let {product ->
                        linkProductToUserInteractor.invoke(it, product)
                    }

                }

                command.value = LoginCommand.LoginSuccess
            } catch (e: Exception) {
                Timber.e(e)
                command.value = LoginCommand.LoginFailed(getFirebaseExceptionResult(e))
            } finally {
                newState { copy(loading = false) }
            }
        }
    }

}

data class LoginState
    (val loading : Boolean = false
)

sealed class LoginCommand{
    data class LoginFailed(val message: Int) : LoginCommand()
    object LoginSuccess : LoginCommand()
}
