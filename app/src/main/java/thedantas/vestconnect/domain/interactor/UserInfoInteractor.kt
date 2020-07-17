package thedantas.vestconnect.domain.interactor

import thedantas.vestconnect.data.data_source.UserInfoLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserInfoInteractor constructor(
    private val userInfoLocalDataSource: UserInfoLocalDataSource
) {

    suspend fun setUserInfo(username: String, userEmail: String) =
        withContext(Dispatchers.Default) {
            userInfoLocalDataSource.setUserInfo(username, userEmail)
        }

    suspend fun getUsername() = withContext(Dispatchers.Default) {
        userInfoLocalDataSource.getUsername()
    }

    suspend fun getUserEmail() = withContext(Dispatchers.Default) {
        userInfoLocalDataSource.getUserEmail()
    }

    suspend fun clearData() = withContext(Dispatchers.Default) {
        userInfoLocalDataSource.clear()
    }

}
