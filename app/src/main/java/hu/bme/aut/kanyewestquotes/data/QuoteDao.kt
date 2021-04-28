package hu.bme.aut.kanyewestquotes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: Quote): Long

    @Query("SELECT * FROM quotes")
    suspend fun getFavouriteQuotes(): List<Quote>

    @Query("SELECT * FROM quotes WHERE quote = :quote")
    suspend fun getFavouriteQuotesByText(quote: String): List<Quote>

    @Query("DELETE FROM quotes WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}