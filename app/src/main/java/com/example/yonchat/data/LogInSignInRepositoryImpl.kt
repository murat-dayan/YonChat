package com.example.yonchat.data

import com.example.yonchat.domain.repository.LogInSignInRepository
import com.example.yonchat.utils.LoginState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInSignInRepositoryImpl @Inject constructor(
    private val firebaseAuth:FirebaseAuth
): LogInSignInRepository {
    override suspend fun login(email: String, password: String): LoginState {
        return  try {
            firebaseAuth.signInWithEmailAndPassword(email,password).await()
            LoginState.Success
        }catch (e:Exception){
            LoginState.Error(e.message)
        }
    }


}