package com.example.internwork3.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.internwork3.data.model.AddToCartRequest
import com.example.internwork3.data.model.ProductUI
import com.example.internwork3.data.repository.ProductRepository
import com.example.internwork3.ui.viewmodel.BaseViewModel
import com.example.internwork3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,application: Application
) : BaseViewModel(application){

    private var _productDetailState = MutableLiveData<ProductDetailState>()
    val productDetailState: LiveData<ProductDetailState>
        get() = _productDetailState  //get is accessor , it can access the object,but can't change.
// This situation This ensures that this is a read-only structure.

    fun getProductsDetail(id: Int) {
        launch {
            _productDetailState.value = ProductDetailState.Loading
            val result = productRepository.getProductsDetail(id)

            when(result) {
                is Resource.Success -> {
                    _productDetailState.value = ProductDetailState.Data(result.data)
                }

                is Resource.Error -> {
                    _productDetailState.value = ProductDetailState.Error(result.throwable)
                }
            }
        }
    }

    //cart eklesin

    fun addProductToCart(addToCartRequest: AddToCartRequest) {
        launch {
            productRepository.addProductToCart(addToCartRequest)
        }
    }




}


sealed interface ProductDetailState {
    object Loading: ProductDetailState
    data class Data(val productResponse: ProductUI) : ProductDetailState //productResponse will go to viewModel obserdata for  use in Data state
    data class Error( val trowable: Throwable): ProductDetailState
}