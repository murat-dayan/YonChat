package com.example.yonchat.presentation.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yonchat.di.Application
import com.example.yonchat.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val firebaseAuth: FirebaseAuth
): AndroidViewModel(application) {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState : LiveData<LoginState> get() = _loginState

    fun logInWithFirebase(email:String,password:String){
        _loginState.value = LoginState.Loading

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                _loginState.value = LoginState.Success
            }
            .addOnFailureListener {
                _loginState.value = LoginState.Error(it.message)
            }
    }
}

sealed class LoginState{
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message:String?) : LoginState()
}