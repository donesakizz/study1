package com.example.internwork3.data.source.remote

import com.example.internwork3.data.model.AddToCartRequest
import com.example.internwork3.data.model.CRUDResponse
import com.example.internwork3.data.model.DeleteFromCartRequest
import com.example.internwork3.data.model.GetProductDetailResponse
import com.example.internwork3.data.model.GetProductsResponse
import com.example.internwork3.util.Constans.Endpoint.ADD_CART_PRODUCTS
import com.example.internwork3.util.Constans.Endpoint.DELETE_CART_PRODUCTS
import com.example.internwork3.util.Constans.Endpoint.GET_CART_PRODUCTS
import com.example.internwork3.util.Constans.Endpoint.GET_PRODUCTS
import com.example.internwork3.util.Constans.Endpoint.GET_PRODUCTS_BY_CATEGORY
import com.example.internwork3.util.Constans.Endpoint.GET_PRODUCT_DETAIL
import com.example.internwork3.util.Constans.Endpoint.GET_SALE_PRODUCTS
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


//write after some create models data class
interface ProductService {
    @GET(GET_PRODUCTS)
    suspend fun getProducts(): GetProductsResponse

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(@Query("id") id: Int): GetProductDetailResponse

    @GET(GET_PRODUCTS_BY_CATEGORY)
    suspend fun getProductsByCategory(@Query("category") categoryValue: String): GetProductsResponse

    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(@Query("userId") userId: String) : GetProductsResponse


    @POST(ADD_CART_PRODUCTS)
    suspend fun addProductToCart(@Body request: AddToCartRequest): CRUDResponse

    @POST(DELETE_CART_PRODUCTS)
    suspend fun deleteProductFromCart(@Body request: DeleteFromCartRequest): CRUDResponse //response





}