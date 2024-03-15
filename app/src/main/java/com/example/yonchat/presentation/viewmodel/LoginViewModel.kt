package com.example.yonchat.presentation.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yonchat.di.Application
import com.example.yonchat.domain.repository.LogInSignInRepository
import com.example.yonchat.utils.SignState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val logInSignInRepository: LogInSignInRepository
): AndroidViewModel(application) {

    private val _signState = MutableLiveData<SignState>()
    val signState : LiveData<SignState> get() = _signState


    fun loginWithFirebase(email:String,password:String){
        viewModelScope.launch {
            _signState.value = SignState.Loading
            val result = logInSignInRepository.login(email,password)

            when(result){
                is SignState.Success->_signState.value = SignState.Success
                is SignState.Error -> _signState.value = SignState.Error(result.message,result.errorCode)
                else->_signState.value = SignState.Loading
            }

        }
    }

    fun signInWithFirebase(email:String,password:String){
        viewModelScope.launch {
            _signState.value = SignState.Loading
            val result = logInSignInRepository.sign(email,password)

            when(result){
                is SignState.Success->_signState.value = SignState.Success
                is SignState.Error -> _signState.value = SignState.Error(result.message,result.errorCode)
                else->_signState.value = SignState.Loading
            }

        }
    }

    fun signOutWithFirebase():Boolean{
        var result= true
        viewModelScope.launch {
            result = logInSignInRepository.signOut()
        }
        return  result
    }
}

