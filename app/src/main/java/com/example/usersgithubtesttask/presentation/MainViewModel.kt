package com.example.usersgithubtesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usersgithubtesttask.data.UserItemData
import com.example.usersgithubtesttask.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {


    val _userItem = MutableLiveData<UserItemData>()
    val userItem: LiveData<UserItemData>
        get() = _userItem

    fun getAllUsers() = repository.getAllUserData()


    fun getUserData(name: String) = repository.getUserData(name)



}