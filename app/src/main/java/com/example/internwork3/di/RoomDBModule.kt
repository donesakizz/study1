package com.example.internwork3.di

import android.content.Context
import androidx.room.Room
import com.example.internwork3.data.source.local.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Provides
    @Singleton

    fun provideRoomDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductRoomDB::class.java,"product_room_db").build()

    @Provides
    @Singleton

    fun provideDao(roomDB: ProductRoomDB) = roomDB.productsDao()
}

//After write ProductRoomDB Without add this class give error about missing binding usage ,is injected at ,(q,productDao) , is enjected at (productRepository,q)
//While cpt was already giving the error that productDao could not be solved, it also suggested this as the 2nd step for the continuation of the solution.
//Provide the AppDatabase in a module:
//Ensure that you have a module to provide the AppDatabase and ProductDao instances.
//Final step have said by CPT : Check the Hilt setup:
//
//Ensure that your application class is annotated with @HiltAndroidApp.
// @HiltAndroidApp class MyApplication : Application()
