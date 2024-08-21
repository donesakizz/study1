package com.example.internwork3.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "price")
    val price: Double?,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "category")
    val category: String?,

    @ColumnInfo(name = "imageOne")
    val imageOne: String?,

    @ColumnInfo(name = "imageTwo")
    val imageTwo: String?,

    @ColumnInfo(name = "imageThree")
    val imageThree: String?,

    @ColumnInfo(name = "rate")
    val rate: Double?,

    @ColumnInfo(name = "count")
    val count: Int?,

    @ColumnInfo(name = "saleState")
    val saleState: Boolean?

)  {
    //This function translates a product entity into a product UI element.
    fun mapToProductUI(): ProductUI {
        return ProductUI(
            id = id ?: 1,
            title = title.orEmpty(),
            price = price ?: 0.0,
            salePrice = salePrice ?: 0.0,
            description = description.orEmpty(),
            category = category.orEmpty(),
            imageOne = imageOne.orEmpty(),
            imageTwo = imageTwo.orEmpty(),
            imageThree = imageThree.orEmpty(),
            rate = rate ?: 0.0,
            count = count ?:1,
            saleState = saleState ?: false
            //If you don't provide all the required data or if you forget any of it, it throws an error because it's tied to an API.
        )
    }
}