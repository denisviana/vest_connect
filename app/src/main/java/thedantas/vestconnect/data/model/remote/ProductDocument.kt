package thedantas.vestconnect.data.model.remote

import java.util.*

data class ProductDocument(
    val title : String = "",
    val type : String = "",
    val category : String = "",
    val content : String = "",
    val registerDate : String = "",
    val contact : String = "",
    val description : String = "",
    val detail : String = "",
    val experitionDate : String = "",
    val fabricator : String = "",
    val identify : String = "",
    val image1 : String = "",
    val image2 : String = "",
    val linkVideo1 : String = "",
    val linkVideo2 : String = "",
    val linkPromotion : String = "",
    val owner : String = "",
    val primaryColor : String = "",
    val secondaryColor : String = "",
    val status : Boolean = false,
    val tag : String = ""
)