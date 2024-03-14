package com.example.yonchat.domain.repository

import com.example.yonchat.utils.LoginState

interface LogInSignInRepository {

    suspend fun login(email:String,password:String): LoginState
}