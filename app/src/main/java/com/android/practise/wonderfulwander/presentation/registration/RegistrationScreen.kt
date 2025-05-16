package com.android.practise.wonderfulwander.presentation.registration

import android.widget.Toast
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.android.practise.wonderfulwander.R
import com.example.base.action.register.RegistrationAction
import com.example.base.event.register.RegistrationEvent
import com.example.base.model.user.RegisterUserParam
import com.example.presentation.viewmodel.RegisterViewModel

@Composable
fun RegistrationScreen(
    onButtonClick: () -> Unit,
    registerViewModel: RegisterViewModel
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cpassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }


    val icon = if (passwordVisibility) {
        painterResource(R.drawable.ic_visibility_foreground)
    } else painterResource(R.drawable.ic_visibility_off_foreground)

    LaunchedEffect(Unit) {
        registerViewModel.event.collect { event ->
            when (event) {
                is RegistrationEvent.NavigateToMapScreen -> {
                    onButtonClick()
                }
                is RegistrationEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
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

                Text(
                    text = "Register",
                    style = MaterialTheme.typography.displayLarge
                )

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Username",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = { newText -> username = newText },
                        placeholder = { Text(text = "Username") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = ""
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 24.dp)
                            .fillMaxWidth(),
                        shape = CircleShape.copy(
                            topStart = CornerSize(2.dp),
                            bottomEnd = CornerSize(2.dp)
                        ),
                    )
                }

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
                        shape = CircleShape.copy(topStart = CornerSize(2.dp), bottomEnd = CornerSize(2.dp))
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Repeat Password",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp)
                    )

                    OutlinedTextField(
                        value = cpassword,
                        onValueChange = { newText -> cpassword = newText },
                        placeholder = { Text(text = "Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
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
                        shape = CircleShape.copy(topStart = CornerSize(2.dp), bottomEnd = CornerSize(2.dp))
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "FirstName",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { newText -> firstName = newText },
                        placeholder = { Text(text = "Username") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = ""
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 24.dp)
                            .fillMaxWidth(),
                        shape = CircleShape.copy(
                            topStart = CornerSize(2.dp),
                            bottomEnd = CornerSize(2.dp)
                        ),
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "LastName",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { newText -> lastName = newText },
                        placeholder = { Text(text = "Username") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = ""
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 24.dp)
                            .fillMaxWidth(),
                        shape = CircleShape.copy(
                            topStart = CornerSize(2.dp),
                            bottomEnd = CornerSize(2.dp)
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Button(
                        onClick = {
                            registerViewModel.updateState(
                                RegisterUserParam(
                                    username = username,
                                    password = password,
                                    confirmPassword = cpassword,
                                    email = email,
                                    firstName = firstName,
                                    lastName = lastName,
                                )
                            )
                            registerViewModel.onAction(RegistrationAction.SubmitRegistration)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 48.dp),
                        shape = CircleShape.copy(CornerSize(10.dp))
                    ) {
                        Text(text = "register")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp,Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "or continue with",
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterHorizontally),
                ){
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(painter = painterResource(R.drawable.google_icon), contentDescription = "google", tint = Color.Unspecified)
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(painter = painterResource(R.drawable.vk_icon), contentDescription = "vk",tint = Color.Unspecified)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Do you already have an account?",
                        modifier = Modifier
                            .clickable {  },
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = FontStyle.Italic
                    )
//                    TextButton(
//                        onClick = {}
//                    ) {
//                        Text(text = "Sign up",
//                            softWrap = true)
//                    }
//
//                    Text(text = "Sign up", modifier = Modifier.clickable {  })
                }
            }
        }
    }
}