package com.example.internwork3.data.model

data class GetProductDetailResponse(
    val status: Int?,
    val message: String?,
    val product: Product? //obje
)

//Write after ProductEntity, ProductUI, Product and GetProductResponse