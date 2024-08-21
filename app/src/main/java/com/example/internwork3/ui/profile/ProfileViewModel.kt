package com.example.internwork3.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internwork3.data.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/*
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel(){

    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile : LiveData<UserProfile?> = _userProfile

    fun fetchUserProfile(userId: String) {
        viewModelScope.launch {

        }
    }
}

 */