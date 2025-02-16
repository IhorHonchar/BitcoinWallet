package ua.honchar.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.honchar.db.entity.TransactionDB

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: TransactionDB)

    @Query("select * from transactions order by fullTime desc")
    fun getTransactions(): Flow<List<TransactionDB>>
}