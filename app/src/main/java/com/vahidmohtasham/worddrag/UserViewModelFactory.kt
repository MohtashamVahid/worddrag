package com.vahidmohtasham.worddrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vahidmohtasham.worddrag.api.UserRepository
import com.vahidmohtasham.worddrag.api.UserViewModel

class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
