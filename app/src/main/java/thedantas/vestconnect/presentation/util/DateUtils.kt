package thedantas.vestconnect.presentation.util
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.ResolverStyle
import java.util.*

const val PATTERN_DATE_TIME = "dd/MM - HH'h'mm"
const val PATTERN_SLASH_DATE = "dd/MM/yyyy"

fun locale() = Locale("pt", "BR")

fun zoneId() = ZoneId.systemDefault() //ZoneId.of("America/Sao_Paulo")

fun isValidDate(dateStr: String): Boolean {
    val formatter = DateTimeFormatter
        .ofPattern("dd/MM/uuuu")
        .withResolverStyle(ResolverStyle.STRICT)

    try {
        LocalDate.parse(dateStr, formatter)
    } catch (e: Exception) {
        return false
    }

    return true
}

fun formatDate(date: Date): String {
    return formatLocalDate(dateToLocalDate(date), PATTERN_DATE_TIME)
}

fun formatLocalDate(localDate: LocalDateTime, pattern: String): String {
    return localDate.format(DateTimeFormatter.ofPattern(pattern).withLocale(locale()))
}

fun dateToLocalDate(date: Date): LocalDateTime {
    return Instant.ofEpochMilli(date.time)
        .atZone(zoneId())
        .toLocalDateTime()
}

fun getCurrentTimeInMillis(): Long {
    return Instant.now().toEpochMilli()
}

fun isTodayDate(date: Date): Boolean {
    val formattedDate = dateToLocalDate(date).toLocalDate()
    val currentDate = LocalDate.now(zoneId())
    if (currentDate.isEqual(formattedDate)) {
        return true
    }
    return false
}

fun parseLocalDate(date: String, pattern: String = PATTERN_SLASH_DATE): LocalDate {
    val dtf = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(date, dtf)
}

/**
 * Retuns a string formatted with the pattern dd/MM/yyyy
 *
 * @param year the selected year
 * @param month the selected month (1-12)
 * @param year the selected day of the month (1-31, depending on month)
 */
fun getDisplayDate(year: Int, month: Int, dayOfMonth: Int): String {
    return DateTimeFormatter.ofPattern(PATTERN_SLASH_DATE)
        .format(LocalDate.of(year, month, dayOfMonth))
}
