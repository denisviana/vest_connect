package thedantas.vestconnect.data.data_source
import android.content.SharedPreferences
import androidx.core.content.edit

class UserInfoLocalDataSource constructor(
    private val preferences: SharedPreferences
) {

    companion object {
        private const val PREF_USERNAME = "pref_username"
        private const val PREF_USER_EMAIL = "pref_user_email"
    }

    fun setUserInfo(username: String, userEmail: String) {
        preferences.edit(commit = true) {
            putString(PREF_USERNAME, username)
            putString(PREF_USER_EMAIL, userEmail)
        }
    }

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
