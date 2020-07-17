package thedantas.vestconnect.data.model.mapper
import thedantas.vestconnect.data.model.domain.UserRegister
import thedantas.vestconnect.data.model.remote.UserDocument

fun UserRegister.toDocument(): UserDocument {
    return UserDocument(
        name = name,
        email = email,
        birthDate = birthDate
    )
}

fun UserDocument.toDomain(): UserRegister {
    return UserRegister(
        name = name,
        email = email,
        birthDate = birthDate,
        password = ""
    )
}
