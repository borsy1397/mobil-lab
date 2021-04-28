package hu.bme.aut.kanyewestquotes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.kanyewestquotes.R
import hu.bme.aut.kanyewestquotes.model.RandomQuote
import hu.bme.aut.kanyewestquotes.utils.DataState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RandomQuoteActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    private val viewModel: RandomQuoteViewModel by viewModels()

    private lateinit var randomQuote: RandomQuote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Random quote"

        subscribeObservers()
        setOnClickListeners()

        val quoteText = intent.getStringExtra("quote")
        if (quoteText != null) {
            randomQuote = RandomQuote(quoteText)
            textView.text = "\"${quoteText}\""
        } else {
            viewModel.setStateEvent(RandomQuoteStateEvent.GetRandomQuote)
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun setOnClickListeners() {
        bottom_navigation.selectedItemId = R.id.itemNewQuote

        bottom_navigation.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.itemNewQuote -> {
                    true
                }
                R.id.itemFavourite -> {
                    startActivity(Intent(this, FavouriteQuotesActivity::class.java))
                    true
                }
                R.id.itemInformation -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }


        btnNew.setOnClickListener {
            viewModel.setStateEvent(RandomQuoteStateEvent.GetRandomQuote)
        }

        btnFavourite.setOnClickListener {
            viewModel.setStateEvent(RandomQuoteStateEvent.AddFavouriteQuote(randomQuote))
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<RandomQuote> -> {
                    displayProgressBar(false)
                    randomQuote = dataState.data
                    textView.text = "\"${randomQuote.quote}\""
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
}