package com.example.base.action.login

import android.content.Intent


sealed class LoginAction {
    data class UpdatePasswordField(val input: String): LoginAction()
    data class UpdateUserNameField(val input: String): LoginAction()
    data object SubmitLoginButton: LoginAction()

    data class SignInWithIntent(val intent: Intent): LoginAction()

    data object SignInWithGoogle: LoginAction()
}