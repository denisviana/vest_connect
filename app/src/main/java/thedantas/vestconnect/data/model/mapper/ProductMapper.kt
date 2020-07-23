package thedantas.vestconnect.data.model.mapper

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import thedantas.vestconnect.data.model.remote.ProductDocument
import thedantas.vestconnect.domain.entity.Product
import thedantas.vestconnect.presentation.util.parseLocalDate

fun ProductDocument.toDomain() : Product{
    return Product(
        name = title,
        category = category,
        contact = contact,
        description = description,
        detail = detail,
        expireDate = LocalDate.parse(experitionDate, DateTimeFormatter.ISO_DATE_TIME),
        fabricator = fabricator,
        identify = identify,
        image1 = image1,
        image2 = image2,
        linkPromotion = linkPromotion,
        linkVideo1 = linkVideo1,
        linkVideo2 = linkVideo2,
        owner = owner,
        primaryColor = primaryColor,
        registerDate = LocalDate.parse(registerDate, DateTimeFormatter.ISO_DATE_TIME),
        secondaryColor = secondaryColor,
        status = status,
        tag = tag,
        type = type
    )
}