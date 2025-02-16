package ua.honchar.domain.repository

import ua.honchar.common.Resource
import ua.honchar.domain.model.TransactionCategory

interface AddTransactionRepository {
    suspend fun addTransaction(amount: Double, category: TransactionCategory): Resource<Unit>
}