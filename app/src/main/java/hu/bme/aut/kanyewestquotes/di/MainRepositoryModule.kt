package hu.bme.aut.kanyewestquotes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.kanyewestquotes.data.QuoteDao
import hu.bme.aut.kanyewestquotes.network.QuotesApi
import hu.bme.aut.kanyewestquotes.repository.MainRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainRepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(quoteDao: QuoteDao, quotesApi: QuotesApi): MainRepository {
        return MainRepository(quoteDao, quotesApi)
    }
}