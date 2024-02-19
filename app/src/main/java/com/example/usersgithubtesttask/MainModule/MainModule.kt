package com.example.usersgithubtesttask.MainModule

import com.example.usersgithubtesttask.data.ObjectDataGit
import com.example.usersgithubtesttask.data.getHubQueryApi
import com.example.usersgithubtesttask.domain.Repository
import com.example.usersgithubtesttask.presentation.UsersListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideRepository(api: getHubQueryApi): Repository {
        return Repository(api)
    }

    @Provides
    @Singleton
    fun providesGithubApi(): getHubQueryApi {
        return ObjectDataGit.api
    }


    @Provides
    @Singleton
    fun provideAdapter(): UsersListAdapter = UsersListAdapter()


}