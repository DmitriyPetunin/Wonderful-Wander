package com.android.practise.wonderfulwander

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import com.android.practise.wonderfulwander.presentation.LoginScreen
import com.android.practise.wonderfulwander.presentation.MainScreen
import com.android.practise.wonderfulwander.presentation.RegistrationScreen
import com.android.practise.wonderfulwander.sign_in.GoogleAuthUiClient
import com.android.practise.wonderfulwander.sign_in.SignInViewModel
import com.example.compose.WonderfulWanderTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WonderfulWanderTheme {
                Surface() {
                    MainScreen()
                }
            }
        }
    }
}