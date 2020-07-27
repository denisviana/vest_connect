package thedantas.vestconnect.data.data_source
import com.google.firebase.database.*
import thedantas.vestconnect.data.model.remote.UserDocument
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import matteocrippa.it.karamba.toCamelCase
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import thedantas.vestconnect.data.model.mapper.toDomain
import thedantas.vestconnect.data.model.mapper.toMap
import thedantas.vestconnect.data.model.remote.ProductDocument
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.domain.entity.User
import java.util.*
import kotlin.collections.HashMap

class FirebaseDatabaseDataSource constructor(
    private val database: FirebaseDatabase
) {

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val PRODUCTS_COLLECTION = "products"
    }

    suspend fun createUser(uid: String, user: UserDocument) {
        database.reference
            .child(USERS_COLLECTION)
            .child(uid)
            .setValue(user)
            .await()
    }

    suspend fun linkProductToUser(user: User, product: Product) : Product{

        user.tags.add(product.tag)

        database.reference
            .child(USERS_COLLECTION)
            .child(user.uid)
            .updateChildren(user.toMap())
            .await()

        val ownerChild : Map<String,String> = HashMap<String,String>().apply {
            put("owner", user.uid)
            put("registerDate", LocalDate.now().atStartOfDay().format(DateTimeFormatter.ISO_DATE_TIME))
        }

        database.reference
            .child(PRODUCTS_COLLECTION)
            .child(product.tag.toLowerCase(Locale.ROOT))
            .updateChildren(ownerChild)
            .await()

        return product.copy(owner = user.uid)
    }

    @ExperimentalCoroutinesApi
    suspend fun getUser(uid: String) = callbackFlow<UserDocument>{

        val userRef = database.reference.child("$USERS_COLLECTION/$uid")

        val eventListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.let {
                    val user = it.getValue(UserDocument::class.java)
                    user?.let { it1 -> this@callbackFlow.sendBlocking(it1) }
                }
            }
        }

        userRef.addListenerForSingleValueEvent(eventListener)

        awaitClose{
            userRef.removeEventListener(eventListener)
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun getProductListByUserId(id: String) = callbackFlow<MutableList<Product?>>{

        val productRef = database.reference.child(PRODUCTS_COLLECTION)
            .orderByChild("owner")
            .equalTo(id)

        val eventListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Product?>()
                snapshot.let {
                    it.children.forEach{ data ->
                        val productDocument = data.getValue(ProductDocument::class.java)
                        list.add(productDocument?.toDomain())
                    }
                    this@callbackFlow.sendBlocking(list)
                }
            }
        }

        productRef.addValueEventListener(eventListener)

        awaitClose{
            productRef.removeEventListener(eventListener)
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun getProductByNfcTagId(tagId: String) = callbackFlow<Product>{

        val productRef = database.reference.child("$PRODUCTS_COLLECTION/$tagId")

        val eventListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.let {
                    val productDocument = it.getValue(ProductDocument::class.java)
                    productDocument?.let { it1 -> this@callbackFlow.sendBlocking(it1.toDomain()) }
                }
            }
        }

        productRef.addValueEventListener(eventListener)

        awaitClose{
            productRef.removeEventListener(eventListener)
        }
    }


}
