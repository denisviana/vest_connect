package thedantas.vestconnect.data.helper

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import thedantas.vestconnect.R

class FirebaseAuthExceptionHelper {

    fun handleAuthException(exception: Any): Int {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> handleInvalidCredentialExp(exception)
            is FirebaseAuthInvalidUserException -> handleInvalidUserExp(exception)
            is FirebaseAuthUserCollisionException -> handleUserCollisionExp(exception)
            is FirebaseAuthEmailException -> R.string.auth_error_send_email
            is FirebaseNetworkException -> R.string.error_connection
            is FirebaseAuthRecentLoginRequiredException -> R.string.auth_error_recent_login_needed_exception
            else -> R.string.auth_error_generic
        }
    }

    private fun handleUserCollisionExp(exception: FirebaseAuthUserCollisionException): Int {
        return when (exception.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> R.string.auth_error_email_in_use
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> R.string.auth_error_wrong_credential
            else -> R.string.auth_error_generic
        }
    }

    private fun handleInvalidUserExp(exception: FirebaseAuthInvalidUserException): Int {
        return when (exception.errorCode) {
            "ERROR_USER_DISABLED" -> R.string.auth_error_user_disabled
            "ERROR_USER_NOT_FOUND" -> R.string.auth_error_not_found
            "ERROR_USER_TOKEN_EXPIRED" -> R.string.auth_error_token_expired
            "ERROR_INVALID_USER_TOKEN" -> R.string.auth_error_invalid_token
            else -> R.string.auth_error_generic
        }
    }

    private fun handleInvalidCredentialExp(exception: FirebaseAuthInvalidCredentialsException): Int {
        return when (exception.errorCode) {
            "ERROR_INVALID_EMAIL" -> R.string.auth_error_wrong_email
            "ERROR_WRONG_PASSWORD" -> R.string.auth_error_wrong_credential
            "ERROR_WEAK_PASSWORD" -> R.string.error_password_length
            else -> R.string.auth_error_generic
        }
    }
}
