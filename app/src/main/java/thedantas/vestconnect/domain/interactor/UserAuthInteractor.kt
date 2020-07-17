package thedantas.vestconnect.domain.interactor

import thedantas.vestconnect.data.data_source.FirebaseAuthDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserAuthInteractor constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource
) {

    suspend fun isUserLogged() = withContext(Dispatchers.Default) {
        firebaseAuthDataSource.isUserLogged()
    }

    suspend fun logout() = withContext(Dispatchers.Default) {
        firebaseAuthDataSource.logout()
    }
}
