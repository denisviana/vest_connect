package thedantas.vestconnect.domain.interactor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import thedantas.vestconnect.data.data_source.FirebaseDatabaseDataSource
import thedantas.vestconnect.domain.entity.Product

class GetProductByNfcTagIdInteractor(
    private  val firebaseDatabaseDataSource: FirebaseDatabaseDataSource
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(userId : String) : Product{
        return firebaseDatabaseDataSource.getProductByNfcTagId(userId).first()
    }

}