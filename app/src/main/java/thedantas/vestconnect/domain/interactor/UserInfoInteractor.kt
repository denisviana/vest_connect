package thedantas.vestconnect.domain.interactor

import android.location.Location
import thedantas.vestconnect.data.data_source.UserInfoLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thedantas.vestconnect.domain.entity.User

class UserInfoInteractor constructor(
    private val userInfoLocalDataSource: UserInfoLocalDataSource
) {

    fun setUserLocation(location: Location){
        userInfoLocalDataSource.setUserLocation(location)
    }

    fun getUserCoordinates() : Pair<Float,Float> = userInfoLocalDataSource.getUserCoordinates()

    suspend fun setUserInfo(user : User) =
        withContext(Dispatchers.Default) {
            userInfoLocalDataSource.setUserInfo(user)
        }

    suspend fun getUsername() = withContext(Dispatchers.Default) {
        userInfoLocalDataSource.getUsername()
    }

    suspend fun getUserUid() = withContext(Dispatchers.Default){
        userInfoLocalDataSource.getUserUid()
    }

    suspend fun getUserEmail() = withContext(Dispatchers.Default) {
        userInfoLocalDataSource.getUserEmail()
    }

    suspend fun clearData() = withContext(Dispatchers.Default) {
        userInfoLocalDataSource.clear()
    }

}
