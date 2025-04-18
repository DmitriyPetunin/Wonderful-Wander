package com.android.practise.wonderfulwander.sign_in


import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    val googleAuthUiClient: GoogleAuthUiClient
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _userdata = MutableStateFlow<UserData?>(null)
    val userData = _userdata.asStateFlow()

    private val _signInIntentSender = MutableStateFlow<IntentSender?>(null)
    val signInIntentSender = _signInIntentSender.asStateFlow()


    private val _signInResult = MutableStateFlow<SignInResult?>(null)
    val signInResult = _signInResult.asStateFlow()

//    private val _signOut = MutableStateFlow(false)
//    val signOut = _signOut.asStateFlow()


    private fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }


    fun signInWithIntent(intent: Intent){
        viewModelScope.launch {
            _signInResult.value = googleAuthUiClient.signInWithIntent(intent = intent)
            onSignInResult(_signInResult.value!!)
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun signIn(){
        viewModelScope.launch {
            _signInIntentSender.value = googleAuthUiClient.signIn()
        }
    }

    fun login(email:String,password:String){
        if (email.isEmpty() || password.isEmpty()){
            _state.update { it.copy(
                isSignInSuccessful = false,
                signInError = "Поля email и пароль не могут быть пустыми"
            ) }
            return
        }
        viewModelScope.launch {
            googleAuthUiClient.login(email,password)
        }

    }
    fun register(email:String,password:String){
        if (email.isEmpty() || password.isEmpty()){
            _state.update { it.copy(
                isSignInSuccessful = false,
                signInError = "какая-то фигня"
            ) }
            return
        }
        viewModelScope.launch {
            googleAuthUiClient.register(email,password)
        }
    }

    fun signOut(){
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            _userdata.value = null
        }
    }

    fun getSignedInUser(){
        _userdata.value = googleAuthUiClient.getSignedInUser()
    }
}