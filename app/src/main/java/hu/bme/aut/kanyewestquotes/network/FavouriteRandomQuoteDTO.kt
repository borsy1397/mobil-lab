package hu.bme.aut.kanyewestquotes.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 * @param id 
 * @param quote 
 */
@JsonClass(generateAdapter = true)
data class FavouriteRandomQuoteDTO (
    @Json(name = "id")
    val id: Long? = null,
    @Json(name = "quote")
    val quote: String? = null
)

