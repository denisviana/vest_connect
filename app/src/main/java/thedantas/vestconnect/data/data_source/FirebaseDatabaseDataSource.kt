package thedantas.vestconnect.data.data_source
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import thedantas.vestconnect.data.model.remote.UserDocument
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import thedantas.vestconnect.domain.entity.User

class FirebaseDatabaseDataSource constructor(private val database: FirebaseDatabase) {

    companion object {
        private const val USERS_COLLECTION = "users"
    }

    suspend fun createUser(uid: String, user: User) {
        database.reference.child(USERS_COLLECTION).child(uid).setValue(user).await()
    }

    @ExperimentalCoroutinesApi
    suspend fun getUser(uid: String) = callbackFlow<User>{

        val userRef = database.reference.child("$USERS_COLLECTION/$uid")

        val eventListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.let {
                    val user = it.getValue(User::class.java)
                    user?.let { it1 -> this@callbackFlow.sendBlocking(it1) }
                }
            }
        }

        userRef.addListenerForSingleValueEvent(eventListener)

        awaitClose{
            userRef.removeEventListener(eventListener)
        }
    }


}
