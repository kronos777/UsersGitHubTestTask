package com.example.usersgithubtesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usersgithubtesttask.data.RepositoryData
import com.example.usersgithubtesttask.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val _repositoryItem = MutableLiveData<RepositoryData>()
    val repositoryItem: LiveData<RepositoryData>
        get() = _repositoryItem


    val _repositoryItemList = MutableLiveData<List<RepositoryData>>()
    val repositoryItemList: LiveData<List<RepositoryData>>
        get() = _repositoryItemList

    fun getRepositoryOrg(orgName: String) = repository.getOrgReposList(orgName)



    fun getRepository(firstName: String, lastName: String) = repository.getRepository(firstName, lastName)


}