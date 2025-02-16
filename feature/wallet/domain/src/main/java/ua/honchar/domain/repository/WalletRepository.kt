package ua.honchar.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.honchar.common.Resource
import ua.honchar.domain.model.Transaction

interface WalletRepository {
    suspend fun addCoins(amount: Double): Resource<Unit>
    fun transactions(): Flow<List<Transaction>>
}