package com.example.yonchat.utils

sealed class LoginState{
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message:String?) : LoginState()
}