package thedantas.vestconnect.domain.interactor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import thedantas.vestconnect.data.data_source.FirebaseAuthDataSource
import thedantas.vestconnect.data.data_source.FirebaseDatabaseDataSource
import thedantas.vestconnect.data.model.domain.UserRegister
import thedantas.vestconnect.data.model.mapper.toDocument
import thedantas.vestconnect.domain.entity.User

class RegisterUserInteractor constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
    private val firebaseDatabaseDataSource: FirebaseDatabaseDataSource
) {

    suspend operator fun invoke(user: User) {
        try {
            val auth = firebaseAuthDataSource.createAuthUser(user.email, user.password)
            firebaseAuthDataSource.updateUserName(user.holder)
            firebaseDatabaseDataSource.createUser(auth.user!!.uid, user)
        } catch (e: Exception) {
            firebaseAuthDataSource.deleteUser()
            throw e
        }
    }
}
