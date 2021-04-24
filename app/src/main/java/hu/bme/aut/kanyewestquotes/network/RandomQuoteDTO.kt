package hu.bme.aut.kanyewestquotes.network

/**
 *
 * @param quote
 */

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RandomQuoteDTO (
    @Json(name = "quote")
    val quote: String? = null
)

