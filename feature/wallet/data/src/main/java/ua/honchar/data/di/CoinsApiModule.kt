package ua.honchar.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ua.honchar.data.api.CoinsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinsApiModule {

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CoinsApi {
        return retrofit.create(CoinsApi::class.java)
    }
}