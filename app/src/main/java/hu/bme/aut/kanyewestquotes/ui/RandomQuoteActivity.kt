package hu.bme.aut.kanyewestquotes.ui

import android.content.Intent
import android.os.Bundle
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

        bottom_navigation.selectedItemId = R.id.itemNewQuote

        bottom_navigation.setOnNavigationItemSelectedListener() { item ->
            when(item.itemId) {
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

        subscribeObservers()


//        if (intent.getStringExtra("quote") != null) {
//
//        } else {
//            viewModel.setStateEvent(RandomQuoteStateEvent.AddFavouriteQuote(RandomQuote("random")))
//        }

        viewModel.setStateEvent(RandomQuoteStateEvent.GetRandomQuote)



//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when(item.itemId) {
//                R.id.page_1 -> {
//                    // Respond to navigation item 1 click
//                    true
//                }
//                R.id.page_2 -> {
//                    startActivity(Intent(this, FavouriteQuotesActivity::class.java))
//                    true
//                }
//                else -> false
//            }
//        }
        //viewModel.setStateEvent(RandomQuoteStateEvent.AddFavouriteQuote(RandomQuote("random")))
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<RandomQuote> -> {
                    randomQuote = dataState.data
                    textView.text = randomQuote.quote
                    //startActivity(Intent(this, FavouriteQuotesActivity::class.java))
                }

                is DataState.Error -> {

                }

                is DataState.Loading -> {

                }
            }
        })
    }
}