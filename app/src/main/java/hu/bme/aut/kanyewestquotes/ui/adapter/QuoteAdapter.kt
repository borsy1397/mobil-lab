package hu.bme.aut.kanyewestquotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.kanyewestquotes.R
import hu.bme.aut.kanyewestquotes.model.FavouriteQuote
import kotlinx.android.synthetic.main.quote_row.view.*


class QuoteAdapter : ListAdapter<FavouriteQuote, QuoteAdapter.QuoteViewHolder>(QuoteComparator) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_row, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = getItem(position)

        holder.quote = quote
        holder.tvQuoteName.text = quote.quote
    }

    inner class QuoteViewHolder(quoteView: View) : RecyclerView.ViewHolder(quoteView) {
        val tvQuoteName: TextView = quoteView.tvQuoteName
        val cardView: CardView = quoteView.cardView
        val btnDelete: Button = quoteView.btnDelete

        var quote: FavouriteQuote? = null

        init {
            btnDelete.setOnClickListener {
                quote?.let { listener?.onDeleteClicked(it) }
            }

            cardView.setOnClickListener {
                quote?.let { listener?.onQuoteClicked(it) }
            }
        }
    }

    interface Listener {
        fun onQuoteClicked(quote: FavouriteQuote)
        fun onDeleteClicked(quote: FavouriteQuote)
    }
}


