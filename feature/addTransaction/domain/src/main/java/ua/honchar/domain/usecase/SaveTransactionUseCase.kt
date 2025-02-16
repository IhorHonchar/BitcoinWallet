package ua.honchar.domain.usecase

import ua.honchar.common.Resource
import ua.honchar.domain.model.TransactionCategory
import ua.honchar.domain.repository.AddTransactionRepository
import javax.inject.Inject

class SaveTransactionUseCase @Inject constructor(
    private val repository: AddTransactionRepository
) {
    suspend operator fun invoke(amount: Double, category: TransactionCategory): Resource<Unit> =
        repository.addTransaction(amount, category)
}