package hu.bme.aut.kanyewestquotes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        val DATABASE_NAME: String = "quote_db"
    }

}