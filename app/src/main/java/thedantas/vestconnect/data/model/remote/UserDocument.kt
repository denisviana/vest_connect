package thedantas.vestconnect.data.model.remote

data class UserDocument(
    val holder: String = "",
    val email: String = "",
    val birthDate: Long = 0,
    val latitude : Float = 0f,
    val longitude : Float = 0f
)
