package ua.honchar.domain.usecase

import ua.honchar.common.Resource
import ua.honchar.domain.repository.WalletRepository
import javax.inject.Inject

class SaveIncomeUseCase @Inject constructor(
    private val repository: WalletRepository
) {
    suspend operator fun invoke(amount: Double): Resource<Unit> = repository.addCoins(amount)
}