package thedantas.vestconnect.data.data_source
import android.content.SharedPreferences
import android.location.Location
import androidx.core.content.edit

class UserInfoLocalDataSource constructor(
    private val preferences: SharedPreferences
) {

    companion object {
        private const val PREF_USERNAME = "pref_username"
        private const val PREF_USER_EMAIL = "pref_user_email"
        private const val PREF_USER_LATITUDE = "pref_user_latitude"
        private const val PREF_USER_LONGITUDE = "pref_user_longitude"
    }

    fun setUserInfo(username: String, userEmail: String) {
        preferences.edit(commit = true) {
            putString(PREF_USERNAME, username)
            putString(PREF_USER_EMAIL, userEmail)
        }
    }

    fun setUserLocation(location: Location){
        preferences.edit(commit = true){
            putFloat(PREF_USER_LATITUDE, location.latitude.toFloat())
            putFloat(PREF_USER_LONGITUDE, location.longitude.toFloat())
        }
    }

    fun getUserCoordinates() : Pair<Float, Float> = Pair(
        preferences.getFloat(PREF_USER_LATITUDE, 0f),
        preferences.getFloat(PREF_USER_LONGITUDE, 0f))

    fun getUsername(): String {
        return preferences.getString(PREF_USERNAME, "") ?: ""
    }

    fun getUserEmail(): String {
        return preferences.getString(PREF_USER_EMAIL, "") ?: ""
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
