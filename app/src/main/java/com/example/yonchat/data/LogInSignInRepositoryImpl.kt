package com.example.yonchat.data

import com.example.yonchat.domain.repository.LogInSignInRepository
import com.example.yonchat.utils.SignState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInSignInRepositoryImpl @Inject constructor(
    private val firebaseAuth:FirebaseAuth
): LogInSignInRepository {
    override suspend fun login(email: String, password: String): SignState {
        return  try {
            firebaseAuth.signInWithEmailAndPassword(email,password).await()
            SignState.Success
        }catch (e:FirebaseAuthException){
            SignState.Error(e.message,e.errorCode)
        }
    }

    override suspend fun sign(email: String, password: String): SignState {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            SignState.Success
        }catch (e:FirebaseAuthException){
            SignState.Error(e.message,e.errorCode)
        }
    }

    override suspend fun signOut(): Boolean {
        return try {
            firebaseAuth.signOut()
            true
        }catch (e:Exception){
            false
        }
    }


}