package thedantas.vestconnect.domain.interactor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import thedantas.vestconnect.data.data_source.FirebaseDatabaseDataSource
import thedantas.vestconnect.data.model.remote.UserDocument
import thedantas.vestconnect.data.model.domain.UserRegister
import thedantas.vestconnect.data.model.mapper.toDomain
import thedantas.vestconnect.domain.entity.User

class GetUserInteractor constructor(
    private val firebaseDatabaseDataSource: FirebaseDatabaseDataSource
) {
    @ExperimentalCoroutinesApi
    suspend operator fun invoke(uid: String): User? {
        return firebaseDatabaseDataSource.getUser(uid).first()
    }
}
