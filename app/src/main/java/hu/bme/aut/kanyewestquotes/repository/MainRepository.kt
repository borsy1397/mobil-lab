package hu.bme.aut.kanyewestquotes.repository

import hu.bme.aut.kanyewestquotes.data.Quote
import hu.bme.aut.kanyewestquotes.data.QuoteDao
import hu.bme.aut.kanyewestquotes.model.FavouriteQuote
import hu.bme.aut.kanyewestquotes.model.RandomQuote
import hu.bme.aut.kanyewestquotes.network.QuotesApi
import hu.bme.aut.kanyewestquotes.network.RandomQuoteDTO
import hu.bme.aut.kanyewestquotes.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MainRepository constructor(private val quoteDao: QuoteDao, private val quotesApi: QuotesApi) {

    suspend fun getRandomQuote(): Flow<DataState<RandomQuote>> = flow {
        emit(DataState.Loading)

        val randomQuoteFromApi = quotesApi.getRandomQuote().body()
        val randomQuote = RandomQuote(quote = randomQuoteFromApi?.quote ?: "")
        emit(DataState.Success(randomQuote))

    }.catch { throwable ->
        emit(DataState.Error(throwable as Exception))
    }

    suspend fun addQuoteToFavourites(quote: RandomQuote): Flow<DataState<RandomQuote>> = flow {
        emit(DataState.Loading)

        val quoteDto = RandomQuoteDTO(quote = quote.quote)
        val favouriteQuote = quotesApi.addQuoteToFavourites(quoteDto).body()
        val quoteList = quoteDao.getFavouriteQuotesByText(quote.quote)
        if (quoteList.isEmpty()) {
            val toInsertQuote = Quote(id = favouriteQuote?.id!!, quote = favouriteQuote.quote!!)
            quoteDao.insert(toInsertQuote)
        }
        emit(DataState.Success(quote))

    }.catch { throwable ->
        emit(DataState.Error(throwable as Exception))
    }

    suspend fun showFavourite(quote: RandomQuote): Flow<DataState<RandomQuote>> = flow {
        emit(DataState.Loading)
        emit(DataState.Success(quote))
    }.catch { throwable ->
        emit(DataState.Error(throwable as Exception))
    }

    suspend fun getFavouriteQuotes(): Flow<DataState<List<FavouriteQuote>>> = flow {
        emit(DataState.Loading)
        emit(
            DataState.Success(
                quoteDao.getFavouriteQuotes().map { FavouriteQuote(it.id, it.quote) })
        )
    }.catch { throwable ->
        emit(DataState.Error(throwable as Exception))
    }

    suspend fun deleteFavouriteQuote(quote: Quote): Flow<DataState<List<FavouriteQuote>>> = flow {
        emit(DataState.Loading)
        quotesApi.deleteQuoteFromFavourite(quote.id)
        quoteDao.deleteById(quote.id)
        emit(
            DataState.Success(
                quoteDao.getFavouriteQuotes().map { FavouriteQuote(it.id, it.quote) })
        )

    }.catch { throwable ->
        emit(DataState.Error(throwable as Exception))
    }
}