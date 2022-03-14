package com.example.consumerapptask.di.modules

import android.content.Context
import com.example.consumerapptask.data.repository.Repository
import com.example.consumerapptask.data.repository.RepositoryImpl
import com.example.consumerapptask.data.service.ApiService
import com.example.consumerapptask.data.service.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository and apiservice instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesApiService(context: Context): ApiService {
        return ApiServiceImpl(context)
    }
}
