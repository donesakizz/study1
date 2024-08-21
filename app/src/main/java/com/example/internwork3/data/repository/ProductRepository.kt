package com.example.internwork3.data.repository

import com.example.internwork3.data.model.AddToCartRequest
import com.example.internwork3.data.model.CRUDResponse
import com.example.internwork3.data.model.DeleteFromCartRequest
import com.example.internwork3.data.model.Product
import com.example.internwork3.util.Resource
import com.example.internwork3.data.model.ProductUI
import com.example.internwork3.data.source.local.ProductDao
import com.example.internwork3.data.source.remote.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productDao: ProductDao,
) {

    //There is no meaning without resource and succes which return callback
    suspend fun getProducts(): Resource<List<ProductUI>> {

        return try {
            Resource.Success(productService.getProducts().products?.map { it.mapToProductUI() }.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getProductsDetail(id: Int): Resource<ProductUI> {
        return try {
            productService.getProductDetail(id).product?.let {
                Resource.Success(it.mapToProductUI())
            } ?: kotlin.run {
                Resource.Error(Exception("Ürün bulunamadı"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getProductsByCategory(category: String): Resource<List<ProductUI>>  {
        return try {
            Resource.Success(productService.getProductsByCategory(category).products?.map { it.mapToProductUI() }.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    suspend fun addProductToCart(addToCartRequest: AddToCartRequest) : CRUDResponse {
        return productService.addProductToCart(addToCartRequest)
    }

    suspend fun getCartProduct(userId: String): Resource<List<ProductUI>> {
        return try {
            val response = productService.getCartProducts(userId)
            Resource.Success(response.products?.map { it.mapToProductUI() }.orEmpty())
        } catch (e:Exception) {
            Resource.Error(e)
        }
    }

    //
    suspend fun deleteProductFromCart(request: DeleteFromCartRequest): CRUDResponse {
        return productService.deleteProductFromCart(request)
    }

    suspend fun deleteProductFromFav(product: ProductUI) {
        productDao.deleteProduct(product.mapToProductEntity())
    }

    suspend fun getFavProducts(): Resource<List<ProductUI>> {
       return try {
           Resource.Success(productDao.getProducts().map {
               it.mapToProductUI()
           })
       } catch (e: Exception) {
           Resource.Error(e)
       }
    }

    suspend fun addProductFav(product: ProductUI) {
        productDao.addProduct(product.mapToProductEntity())
    }



}