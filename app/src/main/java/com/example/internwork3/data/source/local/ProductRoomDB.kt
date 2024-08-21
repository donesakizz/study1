package com.example.internwork3.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.internwork3.data.model.ProductEntity


@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase(){

    abstract fun productsDao(): ProductDao
}

//Without add this class it gives error
//BindingMethodProcessingStep was unable to process 'provideRepository(com.example.internwork3.data.source.remote.ProductService,com.example.internwork3.data.source.local.ProductDao)' because 'com.example.internwork3.data.source.local.ProductDao' could not be resolved.
//CPT Say: The error message indicates that the ProductDao class could not be resolved during the dependency injection process. This typically happens when the ProductDao is not properly set up or there is a configuration issue with Hilt.
//Ensure ProductDao is correctly defined and included in the database:
//
//Make sure that ProductDao is part of your Room database class. The Room database should include the DAO in its @Database annotation.