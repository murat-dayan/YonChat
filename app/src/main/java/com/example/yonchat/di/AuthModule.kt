package com.example.yonchat.di

import com.example.yonchat.data.LogInSignInRepositoryImpl
import com.example.yonchat.domain.repository.LogInSignInRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesLogInSignInRepository() : LogInSignInRepository{
        return  LogInSignInRepositoryImpl(FirebaseAuth.getInstance())

    }
}