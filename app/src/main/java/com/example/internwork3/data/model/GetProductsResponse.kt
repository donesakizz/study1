package com.example.internwork3.data.model

data class GetProductsResponse(
    val status: Int?,
    val message: String?,
    val products: List<Product>? //All list(product)
)
//Write after ProductEntity, ProductUI, Product