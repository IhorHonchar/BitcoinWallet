package ua.honchar.domain.usecase

import kotlinx.coroutines.flow.Flow
import ua.honchar.domain.repository.WalletRepository
import javax.inject.Inject

class GetCurrentBalanceUseCase @Inject constructor(
    private val repository: WalletRepository
) {
    operator fun invoke(): Flow<Double> = repository.getBalance()
}