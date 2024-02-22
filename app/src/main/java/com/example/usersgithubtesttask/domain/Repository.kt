package com.example.usersgithubtesttask.domain;

import com.example.usersgithubtesttask.data.getHubQueryApi
import retrofit2.http.Path

class Repository(private val api: getHubQueryApi) {

    fun getAllUserData() = api.getAllUsersData()

    fun getUserData(name: String) = api.getUserData(name)

    fun getOrgReposList(orgName: String) = api.getOrgReposList(orgName)

    fun getRepository(firstName: String, lastName: String) = api.getRepository(firstName, lastName)

}