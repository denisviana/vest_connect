package thedantas.vestconnect.data.model.mapper
import org.threeten.bp.ZoneOffset
import thedantas.vestconnect.data.model.remote.UserDocument
import thedantas.vestconnect.domain.entity.User
import thedantas.vestconnect.presentation.util.dateToLocalDate
import java.util.*

fun User.toDocument(latitude : Float, longitude : Float): UserDocument {
    return UserDocument(
        holder = holder,
        email = email,
        birthDate = birthday.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
        latitude = latitude,
        longitude = longitude
    )
}

fun UserDocument.toDomain(): User {
    return User(
        holder = holder,
        email = email,
        birthday = dateToLocalDate(Date(birthDate)).toLocalDate(),
        latitude = latitude,
        longitude = longitude,
        password = ""
    )
}
