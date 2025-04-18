package com.android.practise.wonderfulwander.presentation

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.practise.wonderfulwander.R
import com.android.practise.wonderfulwander.navigation.Screen
import com.android.practise.wonderfulwander.sign_in.SignInViewModel


@Composable
fun LoginScreen(
    signInViewModel: SignInViewModel,
    controller:NavHostController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val state by signInViewModel.state.collectAsState()
    val user by signInViewModel.userData.collectAsState()
    val signInIntentSender by  signInViewModel.signInIntentSender.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                signInViewModel.signInWithIntent(result.data!!)
            }
        }
    )

    val icon = if (passwordVisibility) {
        painterResource(R.drawable.ic_visibility_foreground)
    } else painterResource(R.drawable.ic_visibility_off_foreground)

    LaunchedEffect(Unit) {
        signInViewModel.getSignedInUser()
    }

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = user) {
        if (user?.userId != null){
            controller.navigate(Screen.ProfileScreen.route)
        }
    }

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            controller.navigate(Screen.ProfileScreen.route)
            signInViewModel.resetState()
        }
    }

    LaunchedEffect(key1 = signInIntentSender) {
        if (signInIntentSender != null){
            launcher.launch(
                IntentSenderRequest.Builder(signInIntentSender!!).build()
            )
        }
    }



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
                            text = "Email",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp)
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = { newText -> email = newText },
                            placeholder = { Text(text = "Email") },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(top = 24.dp),
                            shape = CircleShape.copy(
                                topStart = CornerSize(2.dp),
                                bottomEnd = CornerSize(2.dp)
                            )
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
                            onValueChange = { newText -> password = newText },
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(top = 24.dp),
                            shape = CircleShape.copy(
                                topStart = CornerSize(2.dp),
                                bottomEnd = CornerSize(2.dp)
                            )
                        )
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            Checkbox(
//                                checked = checked,
//                                onCheckedChange = { checked = it },
//                                modifier = Modifier
//                                    .padding(start = 12.dp)
//                                    .size(8.dp)
//                            )
//                            Text(
//                                text = "Remember me",
//                                modifier = Modifier
//                                    .padding(start = 20.dp)
//                            )
                        }
                        Text(
                            text = "Forgot Password ?",
                            modifier = Modifier
                                .clickable { },
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
                        onClick = {},
                        shape = CircleShape.copy(CornerSize(10.dp))
                    ) {
                        Text(
                            text = "login",
                            modifier = Modifier,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
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
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                    ) {
                        IconButton(
                            onClick = {signInViewModel.signIn()},
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.google_icon),
                                contentDescription = "google",
                                tint = Color.Unspecified
                            )
                        }

                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.vk_icon),
                                contentDescription = "vk",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
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