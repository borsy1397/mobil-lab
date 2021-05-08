package hu.bme.aut.kanyewestquotes


import hu.bme.aut.kanyewestquotes.data.Quote
import hu.bme.aut.kanyewestquotes.data.QuoteDao
import hu.bme.aut.kanyewestquotes.model.FavouriteQuote
import hu.bme.aut.kanyewestquotes.model.RandomQuote
import hu.bme.aut.kanyewestquotes.network.FavouriteRandomQuoteDTO
import hu.bme.aut.kanyewestquotes.network.QuotesApi
import hu.bme.aut.kanyewestquotes.network.RandomQuoteDTO
import hu.bme.aut.kanyewestquotes.repository.MainRepository
import hu.bme.aut.kanyewestquotes.utils.DataState
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import retrofit2.Response

class MainRepositoryTest : BehaviorSpec({
    val mockQuoteDao: QuoteDao = mockk()
    val mockQuotesApi: QuotesApi = mockk()

    val mainRepository = MainRepository(
        quoteDao = mockQuoteDao,
        quotesApi = mockQuotesApi
    )

    given("Get a random quote") {
        coEvery { mockQuotesApi.getRandomQuote() } returns Response.success(RandomQuoteDTO("random quote"))

        When("Check data states") {
            val result = mainRepository.getRandomQuote()

            Then("DataState.Loading.") {
                result.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("DataState.Success<RandomQuote>") {
                result.drop(1).first().shouldBe(
                    DataState.Success(
                        data = RandomQuote("random quote")
                    )
                )
            }
        }
    }

    given("Show favourite quote") {
        When("Check data states") {
            val result = mainRepository.showFavourite(RandomQuote(quote = "random quote"))

            Then("DataState.Loading.") {
                result.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("DataState.Success<RandomQuote>") {
                result.drop(1).first().shouldBe(
                    DataState.Success(
                        data = RandomQuote("random quote")
                    )
                )
            }
        }
    }

    given("Add quote to favourites") {
        coEvery { mockQuotesApi.addQuoteToFavourites(RandomQuoteDTO("random quote")) } returns Response.success(
            FavouriteRandomQuoteDTO(1L, "random quote")
        )
        coEvery { mockQuoteDao.getFavouriteQuotesByText("random quote") } returns listOf()
        coEvery { mockQuoteDao.insert(Quote(1L, "random quote")) } returns 1L

        When("Check data states") {
            val result = mainRepository.addQuoteToFavourites(RandomQuote("random quote"))

            Then("DataState.Loading.") {
                result.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("DataState.Success<RandomQuote>") {
                result.drop(1).first().shouldBe(
                    DataState.Success(
                        data = RandomQuote("random quote")
                    )
                )
            }
        }
    }

    given("Get favourite quotes") {
        coEvery { mockQuoteDao.getFavouriteQuotes() } returns listOf(
            Quote(1L, "random quote 1"),
            Quote(2L, "random quote 2"),
            Quote(3L, "random quote 3")
        )

        When("Check data states") {
            val result = mainRepository.getFavouriteQuotes()

            Then("DataState.Loading.") {
                result.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("List<FavouriteQuote>") {
                result.drop(1).first().shouldBe(
                    DataState.Success(
                        data = listOf(
                            FavouriteQuote(1L, "random quote 1"),
                            FavouriteQuote(2L, "random quote 2"),
                            FavouriteQuote(3L, "random quote 3")
                        )
                    )
                )
            }
        }
    }

    given("Delete quotes from favourite") {
        coEvery { mockQuotesApi.deleteQuoteFromFavourite(2L) } returns Response.success(Unit)
        coEvery { mockQuoteDao.deleteById(2L) } returns 1
        coEvery { mockQuoteDao.getFavouriteQuotes() } returns listOf(
            Quote(1L, "random quote 1"),
            Quote(3L, "random quote 3")
        )

        When("Check data states") {
            val result = mainRepository.deleteFavouriteQuote(Quote(2L, "random quote"))

            Then("DataState.Loading.") {
                result.first().shouldBeTypeOf<DataState.Loading>()
            }
            and("List<FavouriteQuote>") {
                result.drop(1).first().shouldBe(
                    DataState.Success(
                        data = listOf(
                            FavouriteQuote(1L, "random quote 1"),
                            FavouriteQuote(3L, "random quote 3")
                        )
                    )
                )
            }
        }
    }

}) {
    override fun isolationMode() = IsolationMode.InstancePerLeaf
}
