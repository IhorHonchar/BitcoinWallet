package ua.honchar.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.honchar.data.repository.AddTransactionRepositoryImpl
import ua.honchar.domain.repository.AddTransactionRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AddTransactionRepositoryModule {

    @Binds
    abstract fun bindAddTransactionRepository(repository: AddTransactionRepositoryImpl): AddTransactionRepository
}