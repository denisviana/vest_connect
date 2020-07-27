package thedantas.vestconnect.presentation.helper

import java.util.regex.Matcher
import java.util.regex.Pattern

object YouTubeHelper {
    val youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/"
    val videoIdRegex = arrayOf(
        "\\?vi?=([^&]*)",
        "watch\\?.*v=([^&]*)",
        "(?:embed|vi?)/([^/?]*)",
        "^([A-Za-z0-9\\-]*)"
    )

    fun extractVideoIdFromUrl(url: String): String? {
        val youTubeLinkWithoutProtocolAndDomain =
            youTubeLinkWithoutProtocolAndDomain(url)
        for (regex in videoIdRegex) {
            val compiledPattern: Pattern = Pattern.compile(regex)
            val matcher: Matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain)
            if (matcher.find()) {
                return matcher.group(1)
            }
        }
        return ""
    }

    private fun youTubeLinkWithoutProtocolAndDomain(url: String): String {
        val compiledPattern: Pattern = Pattern.compile(youTubeUrlRegEx)
        val matcher: Matcher = compiledPattern.matcher(url)
        return if (matcher.find()) {
            url.replace(matcher.group(), "")
        } else url
    }
}