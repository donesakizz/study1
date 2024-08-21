package com.example.internwork3.ui.cart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.internwork3.data.model.DeleteFromCartRequest
import com.example.internwork3.data.model.ProductUI
import com.example.internwork3.data.repository.ProductRepository
import com.example.internwork3.ui.viewmodel.BaseViewModel
import com.example.internwork3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
) : BaseViewModel(application) {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState

    private var _totalAmount = MutableLiveData<Double>()
    val totalAmount: LiveData<Double>
        get() = _totalAmount


    fun getCartProducts(userId: String) {
        launch {
            _cartState.value = CartState.Loading
            when (val result = productRepository.getCartProduct(userId)) {
                is Resource.Success -> {
                    _cartState.value = CartState.Data(result.data)
                    _totalAmount.value = result.data.sumOf { it.price }
                }

                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }
                else ->{
                    //ide recommend
                }
            }
        }
    }

    fun deleteProduct(request: DeleteFromCartRequest , price: Double) {
        launch {
            productRepository.deleteProductFromCart(request)
            _totalAmount.value = _totalAmount.value?.minus(price)
        }
    }

    fun onIncreaseClick(price: Double) {
        _totalAmount.value = _totalAmount.value?.plus(price)
    }

    fun onnDecreaseClick(price: Double) {
        _totalAmount.value = _totalAmount.value?.minus(price)
    }

}

sealed interface CartState {
    object Loading : CartState
    data class Data(val products: List<ProductUI>) : CartState
    data class Error(val throwable: Throwable) : CartState
}