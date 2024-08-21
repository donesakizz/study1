package com.example.internwork3.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.internwork3.data.model.GetProductsResponse
import com.example.internwork3.data.model.Product
import com.example.internwork3.data.model.ProductUI
import com.example.internwork3.data.repository.ProductRepository
import com.example.internwork3.ui.viewmodel.BaseViewModel
import com.example.internwork3.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale.Category
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository, application: Application
): BaseViewModel(application){

    private val _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    private var _salesState = MutableLiveData<SalesState>()
    val salesState: LiveData<SalesState>
        get() = _salesState

    init {

    }

    fun getProducts(){
        launch { //It started working only after it was defined in BaseViewModel.
            _homeState.value = HomeState.Loading
            val result = productRepository.getProducts() //In order to use productRepository in the functions here, inject annotation is required and this class must be defined in the constructor.
            // Otherwise it gives an error.

            when ( result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Data(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }

        }

    }//fun getProducts() brackets

    fun getProductsByCategory(category: String) {
        launch {
            _homeState.value = HomeState.Loading
            val result = productRepository.getProductsByCategory(category)

            when(result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Data(result.data)
                }
                is Resource.Error -> HomeState.Error(result.throwable)
            }
        }
    }//fun getProductsByCategory() brackets



    fun addProductToFav(product: ProductUI) {
        launch {
            productRepository.addProductFav(product)
        }
    }


}

sealed interface HomeState {
    object Loading: HomeState
    data class Data(val productsResponse: List<ProductUI>) : HomeState

    data class Error(val throwable: Throwable): HomeState
}

sealed interface SalesState {
    object  Loading: SalesState
    data class Data(val productsResponse: List<ProductUI>) : SalesState//You doit before HomeSales it is became false so you changed .

    data class Error(val throwable: Throwable): SalesState
}