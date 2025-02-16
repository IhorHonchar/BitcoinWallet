package ua.honchar.data.repository

import android.icu.util.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ua.honchar.db.dao.TransactionsDao
import ua.honchar.domain.model.Transaction
import ua.honchar.domain.repository.WalletRepository
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao
) : WalletRepository {

    override fun transactions(): Flow<List<Transaction>> {
        return try {
            dao.getTransactions().map { list ->
                list.map {
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                    val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
                    val calendar = Calendar.getInstance().apply { timeInMillis = it.fullTime }
                    Transaction(
                        amount = it.amount,
                        category = it.category,
                        date = dateFormat.format(calendar.time),
                        time = timeFormat.format(calendar.time)
                    )
                }
            }
        } catch (e: Exception) {
            flow { emptyList<Transaction>() }
        }
    }
}