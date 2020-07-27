package thedantas.vestconnect.presentation.features.nfc_reader

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import thedantas.vestconnect.base.BaseViewModel
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.interactor.GetProductByNfcTagIdInteractor
import timber.log.Timber

class NfcReaderViewModel(
    private val getProductByNfcTagIdInteractorInteractor: GetProductByNfcTagIdInteractor
) : BaseViewModel<NfcReaderState, NfcReaderCommand>() {

    init {
        newState(NfcReaderState())
    }

    @ExperimentalCoroutinesApi
    fun getProductByNfcTagId(tagId : String){

        newState(NfcReaderState(loading = true))

        viewModelScope.launch {

            try {
                newState(NfcReaderState(loading = false))
                val product = getProductByNfcTagIdInteractorInteractor(tagId)
                command.value = NfcReaderCommand.GetProductByNfcTagIdSuccessful(product = product)

            }catch (e : Exception){
                newState(NfcReaderState(loading = false))
                Timber.e(e)
                command.value = NfcReaderCommand.GetProductByNfcTagIdFailed(message = "Erro inesperado")
            }

        }

    }

}

data class NfcReaderState(
    val loading : Boolean = false
)

sealed class NfcReaderCommand{
    data class GetProductByNfcTagIdSuccessful(val product : Product) : NfcReaderCommand()
    data class GetProductByNfcTagIdFailed(val message : String) : NfcReaderCommand()
}