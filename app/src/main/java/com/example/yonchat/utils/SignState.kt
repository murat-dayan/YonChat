package com.example.yonchat.utils

sealed class SignState{
    object Loading : SignState()
    object Success : SignState()
    data class Error(val message:String?,val errorCode:String?) : SignState()
}