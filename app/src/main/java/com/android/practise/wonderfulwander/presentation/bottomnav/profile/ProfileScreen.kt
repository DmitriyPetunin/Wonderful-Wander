package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.base.action.profile.ProfileAction
import com.example.base.event.profile.ProfileEvent
import com.example.base.state.ProfileState
import com.example.navigation.Screen
import com.example.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenRoute(
    navigateToAuthScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    navigateToFriendsScreen: () -> Unit,
    navigateToUpdateScreen: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    val state by profileViewModel.stateProfile.collectAsState()

    val context = LocalContext.current

//    LaunchedEffect(Unit) {
//        profileViewModel.getSignedInUser()
//    }

    LaunchedEffect(Unit) {
        profileViewModel.event.collect { event ->
            when (event) {
                is ProfileEvent.NavigateToFriendsPage -> {
                    navigateToFriendsScreen()
                    Toast.makeText(context, "NavigateToFriendsPage", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToAuthPage -> {
                    navigateToAuthScreen()
                    Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToSubscribersPage -> {

                }

                is ProfileEvent.NavigateToSubscriptionsPage -> {

                }

                is ProfileEvent.NavigateToUpdateScreenPage -> {
                    navigateToUpdateScreen()
                    Toast.makeText(context, "NavigateToUpdateScreenPage", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.ShowError -> {
                    navigateToAuthScreen()
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                ProfileEvent.NavigateToRegisterPage -> {
                    navigateToRegisterScreen()
                    Toast.makeText(context, "NavigateToRegisterPage", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    ProfileScreen(state = state, profileViewModel::onAction)
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Profile",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.displayLarge
        )
        if (state?.avatarUrl != null) {
            AsyncImage(
                model = state?.avatarUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (state?.username != null) {
            Text(
                text = state?.username!!,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = {
            onAction(ProfileAction.SignOut)
        }) {
            Text(text = "Sign out")
        }
        Button(onClick = {
            onAction(ProfileAction.SubmitGetAllFriends)
        }) {
            Text(text = "Получить список друзей")
        }
        Button(onClick = {
            onAction(ProfileAction.SubmitGetAllSubscribers)
        }) {
            Text(text = "Получить список подписчиков")
        }
        Button(onClick = {
            onAction(ProfileAction.SubmitGetAllSubscriptions)
        }) {
            Text(text = "Получить список подписок")
        }
        Button(onClick = {
            onAction(ProfileAction.SubmitUpdateProfileInfo)
        }) {
            Text(text = "Изменить данные профиля")
        }
        Button(onClick = {
            onAction(ProfileAction.SubmitDeleteProfile)
        }) {
            Text(text = "удалить аккаунт")
        }
    }
}