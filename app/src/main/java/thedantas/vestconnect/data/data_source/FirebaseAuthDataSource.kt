package thedantas.vestconnect.data.data_source
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun createAuthUser(email: String, password: String): AuthResult =
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    suspend fun loginUser(email: String, password: String): AuthResult =
        firebaseAuth.signInWithEmailAndPassword(email, password).await()

    suspend fun recoverPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    suspend fun updatePassword(password: String) {
        firebaseAuth.currentUser?.updatePassword(password)?.await()
    }

    suspend fun updateUserName(name: String) {
        val profile = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        firebaseAuth.currentUser?.updateProfile(profile)?.await()
    }

    fun isUserLogged(): Boolean = firebaseAuth.currentUser != null

    fun getUserUid(): String? = firebaseAuth.uid

    fun logout() {
        firebaseAuth.signOut()
    }

    suspend fun deleteUser() {
        firebaseAuth.currentUser?.delete()?.await()
    }
}
