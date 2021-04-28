package hu.bme.aut.kanyewestquotes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.kanyewestquotes.R
import hu.bme.aut.kanyewestquotes.model.FavouriteQuote
import hu.bme.aut.kanyewestquotes.ui.adapter.QuoteAdapter
import hu.bme.aut.kanyewestquotes.utils.DataState
import kotlinx.android.synthetic.main.activity_favourite_quotes.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouriteQuotesActivity : AppCompatActivity(), QuoteAdapter.Listener {

    private lateinit var adapter: QuoteAdapter

    @ExperimentalCoroutinesApi
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_quotes)

        title = "Favourite quotes"

        initializeRecyclerView()

        setOnClickListeners()
        subscribeObservers()

        viewModel.setStateEvent(FavouriteStateEvent.GetFavouriteQuotes)
    }

    private fun initializeRecyclerView() {
        adapter = QuoteAdapter()
        adapter.listener = this
        listQuotes.adapter = adapter
    }

    private fun setOnClickListeners() {
        bottom_navigation.selectedItemId = R.id.itemFavourite

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemNewQuote -> {
                    startActivity(Intent(this, RandomQuoteActivity::class.java))
                    true
                }
                R.id.itemFavourite -> {
                    true
                }
                R.id.itemInformation -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<FavouriteQuote>> -> {
                    displayProgressBar(false)
                    adapter.submitList(dataState.data)
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    override fun onQuoteClicked(quote: FavouriteQuote) {
        val intent = Intent(this, RandomQuoteActivity::class.java)
        intent.putExtra("quote", quote.quote)
        startActivity(intent)
    }

    override fun onDeleteClicked(quote: FavouriteQuote) {
        viewModel.setStateEvent(FavouriteStateEvent.DeleteFavouriteQuote(quote))
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }
}