package com.example.yonchat.presentation.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yonchat.di.Application
import com.example.yonchat.domain.model.User
import com.example.yonchat.domain.repository.LogInSignInRepository
import com.example.yonchat.utils.LoginState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val logInSignInRepository: LogInSignInRepository
): AndroidViewModel(application) {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState : LiveData<LoginState> get() = _loginState


    fun loginWithFirebase(email:String,password:String){
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = logInSignInRepository.login(email,password)

            when(result){
                is LoginState.Success->_loginState.value = LoginState.Success
                is LoginState.Error -> _loginState.value = LoginState.Error(result.message)
                else->_loginState.value = LoginState.Loading
            }

        }
    }
}

