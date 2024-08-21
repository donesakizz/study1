package com.example.internwork3.di

import com.example.internwork3.data.repository.ProductRepository
import com.example.internwork3.data.source.local.ProductDao
import com.example.internwork3.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(productService: ProductService , productDao: ProductDao) :ProductRepository =
        ProductRepository(productService, productDao)
}