package ua.honchar.domain.usecase

import kotlinx.coroutines.flow.Flow
import ua.honchar.domain.model.Transaction
import ua.honchar.domain.repository.WalletRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: WalletRepository
) {

    operator fun invoke(): Flow<List<Transaction>> = repository.transactions()
}