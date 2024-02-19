package com.example.usersgithubtesttask.domain;

import com.example.usersgithubtesttask.data.getHubQueryApi

class Repository(private val api: getHubQueryApi) {

    fun getAllUserData() = api.getAllUsersData()

    fun getUserData(name: String) = api.getUserData(name)

}