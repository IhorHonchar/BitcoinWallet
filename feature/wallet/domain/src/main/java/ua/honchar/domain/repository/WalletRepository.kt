package ua.honchar.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.honchar.common.Resource
import ua.honchar.domain.model.Transaction

interface WalletRepository {
    fun transactionsPaged(): Flow<PagingData<Transaction>>
    fun getBalance(): Flow<Double>
    suspend fun getBitcoinExchangeRate(): Resource<String>
}