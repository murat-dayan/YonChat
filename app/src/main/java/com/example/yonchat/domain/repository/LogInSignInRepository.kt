package com.example.yonchat.domain.repository

import com.example.yonchat.utils.SignState

interface LogInSignInRepository {

    suspend fun login(email:String,password:String): SignState

    suspend fun sign(email: String,password: String):SignState

    suspend fun signOut():Boolean
}