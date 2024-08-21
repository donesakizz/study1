package com.example.internwork3.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internwork3.data.model.UserProfile
import com.example.internwork3.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel(){

    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile : LiveData<UserProfile?>
        get() = _userProfile

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean>
    get() = _updateResult

    fun fetchUserProfile(userId: String) {
        viewModelScope.launch {
            val profile = repository.getUserProfile(userId)
            _userProfile.postValue(profile)
        }
    }

    fun updateUserProfile(userId: String, name:String, phone: String) {
        viewModelScope.launch {
            val result = repository.updateUserProfile(userId, name, phone)
            _updateResult.postValue(result)
        }
    }
}