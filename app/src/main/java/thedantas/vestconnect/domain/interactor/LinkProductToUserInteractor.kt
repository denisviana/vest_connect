package thedantas.vestconnect.domain.interactor

import thedantas.vestconnect.data.data_source.FirebaseDatabaseDataSource
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.entity.User

class LinkProductToUserInteractor(
    private val firebaseDatabaseDataSource: FirebaseDatabaseDataSource
) {

    suspend fun invoke(user : User, product : Product) : Product {
        return firebaseDatabaseDataSource.linkProductToUser(
            user,
            product
        )
    }

}