package com.example.internwork3.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.internwork3.data.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM cart_products")
    suspend fun getProducts(): List<ProductEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)
}