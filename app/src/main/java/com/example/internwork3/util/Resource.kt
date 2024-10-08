package com.example.internwork3.util

import com.example.internwork3.ui.home.HomeState

sealed class Resource <out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()

    data class Error(val throwable: Throwable) : Resource<Nothing>()
}