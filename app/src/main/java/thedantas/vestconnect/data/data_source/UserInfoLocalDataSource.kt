package thedantas.vestconnect.data.data_source
import android.content.SharedPreferences
import android.location.Location
import androidx.core.content.edit
import thedantas.vestconnect.domain.entity.User

class UserInfoLocalDataSource constructor(
    private val preferences: SharedPreferences
) {

    companion object {
        private const val PREF_USERNAME = "pref_username"
        private const val PREF_USER_EMAIL = "pref_user_email"
        private const val PREF_USER_UID = "pref_user_uid"
        private const val PREF_USER_BIRTHDATE = "pref_user_birthdate"
        private const val PREF_USER_LATITUDE = "pref_user_latitude"
        private const val PREF_USER_LONGITUDE = "pref_user_longitude"
        private const val PREF_USER_ONBOARDING = "pref_user_onboarding"
    }

    fun setUserInfo(user : User) {
        preferences.edit(commit = true) {
            putString(PREF_USER_EMAIL, user.email)
            putString(PREF_USER_UID, user.uid)
            putString(PREF_USERNAME, user.holder)
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

    fun getUserUid() : String = preferences.getString(PREF_USER_UID, "") ?: ""

    fun getUserEmail(): String {
        return preferences.getString(PREF_USER_EMAIL, "") ?: ""
    }

    fun setOnboardingIntroShowed(){
        preferences.edit(commit = true){
            putBoolean(PREF_USER_ONBOARDING, true)
        }
    }

    fun wasOnboardingIntroShowed() : Boolean{
        return preferences.getBoolean(
            PREF_USER_ONBOARDING, false
        )
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
