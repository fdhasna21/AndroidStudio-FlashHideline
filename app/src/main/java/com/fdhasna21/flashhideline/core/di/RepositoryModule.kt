package com.fdhasna21.flashhideline.core.di

import com.fdhasna21.flashhideline.data.repository.NewsRepository
import com.fdhasna21.flashhideline.data.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}