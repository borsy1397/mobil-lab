package hu.bme.aut.kanyewestquotes.ui.adapter;

import androidx.recyclerview.widget.DiffUtil
import hu.bme.aut.kanyewestquotes.model.FavouriteQuote

object QuoteComparator : DiffUtil.ItemCallback<FavouriteQuote>() {
    override fun areItemsTheSame(oldItem: FavouriteQuote, newItem: FavouriteQuote): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavouriteQuote, newItem: FavouriteQuote): Boolean {
        return oldItem == newItem
    }
}
