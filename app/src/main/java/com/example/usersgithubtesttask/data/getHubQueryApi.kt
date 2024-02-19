package com.example.usersgithubtesttask.data;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface getHubQueryApi {

    @GET("users")
    fun getAllUsersData(): Single<List<ListItemData>>


    @GET("users/{name}")
    fun getUserData(@Path("name") name: String): Single<UserItemData>

}
