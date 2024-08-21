package com.example.internwork3.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize  //this important
data class Product(
    val id: Int?,
    val title: String?,
    val price: Double?,
    val salePrice: Double?,
    val description: String?,
    val category: String?,
    val imageOne: String?,
    val imageTwo: String?,
    val imageThree:String?,
    val rate: Double?,
    val count: Int?,
    val saleState: Boolean? //discountStatus
)  //Parcelable Function that converts Product class to ProductUI class, I couldn't see it in the user interface when I wasn't using it.
    :Parcelable {
        fun mapToProductUI(): ProductUI {
           return ProductUI(
               id = id ?: 1,  //Even if you don't type(?:) it together, it turns red and gives an error
               title =title.orEmpty(),
               price = price ?:0.0,
               salePrice = salePrice ?: 0.0,
               description = description.orEmpty(),
               category = category.orEmpty(),
               imageOne = imageOne.orEmpty(),
               imageTwo = imageTwo.orEmpty(),
               imageThree = imageThree.orEmpty(),
               rate = rate ?: 0.0,
               count = count ?: 1,
               saleState = saleState ?: false
           )
        }

    }