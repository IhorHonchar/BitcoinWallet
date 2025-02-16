package ua.honchar.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.honchar.domain.model.Transaction

interface WalletRepository {
    fun transactions(): Flow<List<Transaction>>
}