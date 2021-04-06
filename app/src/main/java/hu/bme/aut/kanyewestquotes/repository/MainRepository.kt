package hu.bme.aut.kanyewestquotes.repository

import hu.bme.aut.kanyewestquotes.data.QuoteDao
import hu.bme.aut.kanyewestquotes.network.QuotesApi

class MainRepository constructor(private val quoteDao: QuoteDao, private val quotesApi: QuotesApi) {
    // TODO
}