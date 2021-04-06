package hu.bme.aut.kanyewestquotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "quote") var quote: String
)