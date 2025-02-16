package ua.honchar.db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.honchar.db.AppDatabase
import ua.honchar.db.dao.TransactionsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "wallet-database",
    ).build()

    @Provides
    fun providesTopicsDao(
        database: AppDatabase,
    ): TransactionsDao = database.transactionsDao()
}