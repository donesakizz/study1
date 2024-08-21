package com.example.internwork3.data.model

data class ProductUI(
    val id: Int,
    val title: String,
    val price: Double,
    val salePrice: Double,
    val description: String,
    val category: String,
    val imageOne: String,
    val imageTwo: String,
    val imageThree: String,
    val rate: Double,
    val count: Int,
    val saleState: Boolean
) {
    //This function translates data received from the user interface (productUI) into a product entity.Part of logic.Ease of process
    fun mapToProductEntity(): ProductEntity { //Before write this func. should write ProductEntitiy using room
        return ProductEntity(
            id = id,
            title = title,
            price = price,
            salePrice = salePrice,
            description = description,
            category = category,
            imageOne = imageOne,
            imageTwo = imageTwo,
            imageThree = imageThree,
            rate = rate,
            count = count,
            saleState = saleState
        )
    }
}
