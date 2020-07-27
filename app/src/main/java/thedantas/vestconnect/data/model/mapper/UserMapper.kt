package thedantas.vestconnect.data.model.mapper
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import thedantas.vestconnect.data.model.remote.UserDocument
import thedantas.vestconnect.domain.entity.User
import kotlin.collections.HashMap

fun User.toDocument(latitude : Float, longitude : Float): UserDocument {
    return UserDocument(
        holder = holder,
        email = email,
        birthDate = birthday.atStartOfDay().format(DateTimeFormatter.ISO_DATE_TIME),
        latitude = latitude,
        longitude = longitude
    )
}

fun User.toMap() : Map<String,Any>{
    return HashMap<String,Any>()
        .apply {
            put("holder", holder)
            put("email", email)
            put("birthDate", birthday.atStartOfDay().format(DateTimeFormatter.ISO_DATE_TIME))
            put("latitude", latitude ?: 0)
            put("longitude", longitude ?: 0)
            put("tags", tags)
        }
}

fun UserDocument.toDomain(uid : String): User {
    return User(
        uid = uid,
        holder = holder,
        email = email,
        birthday = LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE_TIME),
        latitude = latitude,
        longitude = longitude,
        password = ""
    )
}
