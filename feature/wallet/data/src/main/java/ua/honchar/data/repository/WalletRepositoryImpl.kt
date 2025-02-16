package ua.honchar.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.honchar.db.dao.TransactionsDao
import ua.honchar.db.entity.TransactionDB
import ua.honchar.domain.model.Transaction
import ua.honchar.domain.repository.WalletRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao
) : WalletRepository {

    override fun transactionsPaged(): Flow<PagingData<Transaction>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 0)) {
            dao.getTransactionsPaged()
        }
            .flow
            .map { value: PagingData<TransactionDB> ->
                value.map {
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
                    val calendar = Calendar.getInstance().apply { timeInMillis = it.fullTime }
                    Transaction(
                        id = it.id,
                        amount = it.amount,
                        category = it.category,
                        date = dateFormat.format(calendar.time),
                        time = timeFormat.format(calendar.time)
                    )
                }
            }
    }

    override fun getBalance(): Flow<Double> {
        return dao.getAllTransactionsAmount().map { it.sum() }
    }

    override fun getBitcoinExchangeRate(): String {

    }
}