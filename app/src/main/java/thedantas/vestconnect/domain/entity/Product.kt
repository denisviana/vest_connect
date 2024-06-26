package thedantas.vestconnect.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

@Parcelize
data class Product(
    val name : String,
    val type : String,
    val category : String,
    val registerDate : LocalDateTime,
    val contact : String,
    val description : String,
    val detail : String,
    val expirationDate : LocalDateTime ? = null,
    val fabricator : String,
    val identify : String,
    val image1 : String,
    val image2 : String,
    val linkVideo1 : String = "",
    val linkVideo2 : String = "",
    val linkPromotion : String,
    val owner : String,
    val primaryColor : String,
    val secondaryColor : String,
    val status : Boolean,
    val tag : String
) : Parcelable