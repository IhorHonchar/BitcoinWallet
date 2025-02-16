package ua.honchar.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.honchar.db.dao.TransactionsDao
import ua.honchar.db.entity.TransactionDB

@Database(entities = [TransactionDB::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
}