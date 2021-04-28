package hu.bme.aut.kanyewestquotes.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.kanyewestquotes.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        title = "Information"

        bottom_navigation.selectedItemId = R.id.itemInformation

        bottom_navigation.setOnNavigationItemSelectedListener() { item ->
            when(item.itemId) {
                R.id.itemNewQuote -> {
                    startActivity(Intent(this, RandomQuoteActivity::class.java))
                    true
                }
                R.id.itemFavourite -> {
                    startActivity(Intent(this, FavouriteQuotesActivity::class.java))
                    true
                }
                R.id.itemInformation -> {
                    true
                }
                else -> false
            }
        }

    }
}