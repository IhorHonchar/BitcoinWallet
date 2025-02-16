package ua.honchar.data.repository

import android.icu.util.Calendar
import ua.honchar.common.Resource
import ua.honchar.common.SaveTransactionException
import ua.honchar.db.dao.TransactionsDao
import ua.honchar.db.entity.TransactionDB
import ua.honchar.domain.model.TransactionCategory
import ua.honchar.domain.repository.AddTransactionRepository
import javax.inject.Inject

class AddTransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao,
) : AddTransactionRepository {

    override suspend fun addTransaction(
        amount: Double,
        category: TransactionCategory
    ): Resource<Unit> {
        val currentDate = Calendar.getInstance()
        val transaction = TransactionDB(
            amount = amount,
            fullTime = currentDate.timeInMillis,
            category = category(),
            date = currentDate.apply {
                clear(Calendar.HOUR)
                clear(Calendar.MINUTE)
                clear(Calendar.SECOND)
                clear(Calendar.MILLISECOND)
            }.timeInMillis
        )
        return try {
            dao.saveTransaction(transaction)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(SaveTransactionException())
        }
    }
}