package hu.bme.aut.kanyewestquotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.kanyewestquotes.R
import hu.bme.aut.kanyewestquotes.model.FavouriteQuote
import hu.bme.aut.kanyewestquotes.model.RandomQuote
import hu.bme.aut.kanyewestquotes.ui.adapter.QuoteAdapter
import hu.bme.aut.kanyewestquotes.utils.DataState
import kotlinx.android.synthetic.main.activity_favourite_quotes.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class FavouriteQuotesActivity : AppCompatActivity(), QuoteAdapter.Listener {

    private lateinit var adapter: QuoteAdapter

    @ExperimentalCoroutinesApi
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_quotes)

        adapter = QuoteAdapter()
        adapter.listener = this
        listQuotes.adapter = adapter

        subscribeObservers()

        viewModel.setStateEvent(FavouriteStateEvent.GetFavouriteQuotes)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<FavouriteQuote>> -> {
                    adapter.submitList(dataState.data)
                }

                is DataState.Error -> {

                }

                is DataState.Loading -> {

                }
            }
        })
    }

    override fun onQuoteClicked(quote: FavouriteQuote) {
        TODO("Not yet implemented")
    }

    override fun onDeleteClicked(quote: FavouriteQuote) {
        viewModel.setStateEvent(FavouriteStateEvent.DeleteFavouriteQuote(quote))
    }
}