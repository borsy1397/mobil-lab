package hu.bme.aut.kanyewestquotes.repository

import hu.bme.aut.kanyewestquotes.data.Quote
import hu.bme.aut.kanyewestquotes.data.QuoteDao
import hu.bme.aut.kanyewestquotes.model.RandomQuote
import hu.bme.aut.kanyewestquotes.network.QuotesApi
import hu.bme.aut.kanyewestquotes.network.RandomQuoteDTO
import hu.bme.aut.kanyewestquotes.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository constructor(private val quoteDao: QuoteDao, private val quotesApi: QuotesApi) {

    suspend fun getRandomQuote(): Flow<DataState<RandomQuote>> = flow {
        emit(DataState.Loading)
        try {
            val randomQuoteFromApi = quotesApi.getRandomQuote().body()
            val randomQuote = RandomQuote(quote = randomQuoteFromApi?.quote ?: "")
            emit(DataState.Success(randomQuote))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun addQuoteToFavourites(quote: RandomQuote): Flow<DataState<RandomQuote>> = flow {
        emit(DataState.Loading)
        try {
            val quoteDto = RandomQuoteDTO(quote = quote.quote)
            val favouriteQuote = quotesApi.addQuoteToFavourites(quoteDto).body()
            val toInsertQuote = Quote(id = favouriteQuote?.id!!, quote = favouriteQuote.quote!!)
            quoteDao.insert(toInsertQuote)
            emit(DataState.Success(quote))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getFavouriteQuotes(): Flow<DataState<List<Quote>>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(quoteDao.getFavouriteQuotes()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun deleteFavouriteQuote(quote: Quote): Flow<DataState<List<Quote>>> = flow {
        try {
            quotesApi.deleteQuoteFromFavourite(quote.id)
            quoteDao.deleteById(quote.id)
            emit(DataState.Success(quoteDao.getFavouriteQuotes()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}