package hu.bme.aut.kanyewestquotes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.kanyewestquotes.R
import hu.bme.aut.kanyewestquotes.model.RandomQuote
import hu.bme.aut.kanyewestquotes.utils.DataState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RandomQuoteActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    private val viewModel: RandomQuoteViewModel by viewModels()

    private lateinit var randomQuote: RandomQuote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()

        //viewModel.setStateEvent(RandomQuoteStateEvent.GetRandomQuote)
        viewModel.setStateEvent(RandomQuoteStateEvent.AddFavouriteQuote(RandomQuote("random")))
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<RandomQuote> -> {
                    randomQuote = dataState.data
                    textView.text = randomQuote.quote
                    startActivity(Intent(this, FavouriteQuotesActivity::class.java))
                }

                is DataState.Error -> {

                }

                is DataState.Loading -> {

                }
            }
        })
    }
}