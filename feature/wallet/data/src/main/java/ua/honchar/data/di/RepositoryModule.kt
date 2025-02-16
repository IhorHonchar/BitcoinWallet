package ua.honchar.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.honchar.data.repository.WalletRepositoryImpl
import ua.honchar.domain.repository.WalletRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindWalletRepository(repository: WalletRepositoryImpl): WalletRepository
}