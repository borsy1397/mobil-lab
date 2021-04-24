package hu.bme.aut.kanyewestquotes.network

import retrofit2.Response
import retrofit2.http.*

interface QuotesApi {
    /**
     * Add the quote to the favourite quotes
     *
     * Responses:
     *  - 201: OK, quote added to favourite
     *  - 409: Conflict, quote already added to favourites
     *
     * @param body
     * @return [FavouriteRandomQuoteDTO]
     */
    @POST(".")
    suspend fun addQuoteToFavourites(@Body body: RandomQuoteDTO): Response<FavouriteRandomQuoteDTO>

    /**
     * Delete a quote from favourite
     *
     * Responses:
     *  - 204: Quote deleted
     *
     * @param id The id of the quote to be deleted from favourites
     * @return [Unit]
     */
    @DELETE("{id}")
    suspend fun deleteQuoteFromFavourite(@Path("id") id: Long): Response<Unit>

    /**
     * Get a random quote
     *
     * Responses:
     *  - 200: OK
     *
     * @return [RandomQuoteDTO]
     */
    @GET(".")
    suspend fun getRandomQuote(): Response<RandomQuoteDTO>

}
