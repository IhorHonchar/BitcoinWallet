package ua.honchar.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullTime: Long,
    val amount: Double,
    val category: String,
    val date: Long,
)
