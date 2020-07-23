package thedantas.vestconnect.domain.entity

import org.threeten.bp.LocalDate

data class Product(
    val name : String,
    val type : String,
    val category : String,
    val registerDate : LocalDate,
    val contact : String,
    val description : String,
    val detail : String,
    val expireDate : LocalDate,
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
)