package com.android.practise.wonderfulwander.presentation.login

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.action.login.LoginAction
import com.example.base.event.login.LoginEvent
import com.example.base.state.LoginState
import com.example.presentation.viewmodel.SignInViewModel

import com.example.base.R as baseR

@Composable
fun LoginScreenRoute(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
){

    val loginState by signInViewModel.state.collectAsState()

    val signInIntentSender by signInViewModel.signInIntentSender.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        signInViewModel.onAction(LoginAction.SubmitLoginButton)
    }
    LaunchedEffect(Unit) {
        navigateToProfile()
    }

    LaunchedEffect(Unit) {
        signInViewModel.event.collect{ event ->
            when(event) {
                LoginEvent.ErrorLogin -> {
                    loginState.signInError?.let { message ->
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }
                LoginEvent.SuccessLogin -> {
                    navigateToProfile()
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()
                }
                LoginEvent.UserExist -> {
                    navigateToProfile()
                    Toast.makeText(context, "UserExist", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { signInViewModel.onAction(LoginAction.SignInWithIntent(it)) }
            }
        }
    )

    LaunchedEffect(key1 = signInIntentSender) {
        if (signInIntentSender != null) {
            signInIntentSender?.let {
                launcher.launch(
                    IntentSenderRequest.Builder(it).build()
                )
            }
        }
    }

    if(loginState.isLoading){
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }else{
        LoginScreen(state = loginState,signInViewModel::onAction)
    }

}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {

    val userName = state.userName
    val password = state.password
    val supportingTextEmail = state.supportingTextUserName
    val supportingTextPassword = state.supportingTextPassword

    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility) {
        painterResource(baseR.drawable.ic_visibility_foreground)
    } else painterResource(baseR.drawable.ic_visibility_off_foreground)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.displayLarge,
                    )
                    Text(
                        text = "Enter your email and password to log in ",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "UserName",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp)
                        )

                        OutlinedTextField(
                            value = userName,
                            onValueChange = { onAction(LoginAction.UpdateUserNameField(it)) },
                            placeholder = { Text(text = "UserName") },
                            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = "") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(top = 24.dp),
                            shape = CircleShape.copy(
                                topStart = CornerSize(2.dp),
                                bottomEnd = CornerSize(2.dp)
                            ),
                            supportingText = {
                                Text(
                                    text = supportingTextEmail.orEmpty(),
                                    color = MaterialTheme.colorScheme.error,
                                )
                            },
                            singleLine = true
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Password",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp)
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { onAction(LoginAction.UpdatePasswordField(it)) },
                            placeholder = { Text(text = "Password") },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                    Icon(painter = icon, contentDescription = "")
                                }
                            },
                            visualTransformation =
                                if (passwordVisibility) {
                                    VisualTransformation.None
                                } else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(top = 24.dp),
                            shape = CircleShape.copy(
                                topStart = CornerSize(2.dp),
                                bottomEnd = CornerSize(2.dp)
                            ),
                            supportingText = {
                                Text(
                                    text = supportingTextPassword.orEmpty(),
                                    color = MaterialTheme.colorScheme.error
                                )
                            },
                            singleLine = true
                        )
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Forgot Password ?",
                            modifier = Modifier
                                .clickable { //TODO(navigate to Forgot PasswordScreen)*//
                                    },
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        onClick = { onAction(LoginAction.SubmitLoginButton) },
                        shape = CircleShape.copy(CornerSize(10.dp)),
                        enabled = state.inputFieldsIsValid
                    ) {
                        Text(
                            text = "login",
                            modifier = Modifier,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f)
                        )
                        Text(
                            text = "Or login with",
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        IconButton(
                            onClick = { onAction(LoginAction.SignInWithGoogle) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(baseR.drawable.google_icon),
                                contentDescription = "google",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don’t have an account?")
                    Text(
                        text = "Sign Up",
                        modifier = Modifier.clickable { },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}