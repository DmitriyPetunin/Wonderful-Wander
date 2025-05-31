package com.android.practise.wonderfulwander.presentation.bottomnav.profile

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.practise.wonderfulwander.presentation.post.CustomDropDawnMenu
import com.example.base.action.profile.ProfileAction
import com.example.base.event.profile.ProfileEvent
import com.example.base.state.ProfileState
import com.example.presentation.viewmodel.ProfileViewModel
import com.example.base.R as baseR

@Composable
fun ProfileScreenRoute(
    navigateToAuthScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    navigateToPeopleScreen: (String) -> Unit,
    navigateToUpdateScreen: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    val state by profileViewModel.stateProfile.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.getSignedInUser()
    }

    LaunchedEffect(Unit) {
        profileViewModel.event.collect { event ->
            when (event) {
                is ProfileEvent.NavigateToFriendsPage -> {
                    navigateToPeopleScreen("friends")
                    Toast.makeText(context, "friends", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToAuthPage -> {
                    navigateToAuthScreen()
                    Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToFollowersPage -> {
                    navigateToPeopleScreen("followers")
                    Toast.makeText(context, "followers", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToFollowingPage -> {
                    navigateToPeopleScreen("following")
                    Toast.makeText(context, "following", Toast.LENGTH_SHORT).show()
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

        TopBar(
            username = state.username,
            modifier = Modifier.fillMaxWidth(),
            updateDropDawnVisible = {onAction(ProfileAction.UpdateDropDawnVisible(isVisible = !state.dropDownMenuVisible))},
            visibleState = state.dropDownMenuVisible
        )

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

        StatSection(state = state,onAction = onAction,modifier = Modifier.weight(7f))


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


@Composable
fun TopBar(
    username: String,
    modifier: Modifier = Modifier,
    updateDropDawnVisible: () -> Unit,
    visibleState:Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = username,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Icon(
            painter = painterResource(id = baseR.drawable.ic_bell),
            contentDescription = "bell",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Icon(
            painter = painterResource(id = baseR.drawable.ic_dotmenu),
            contentDescription = "menu",
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
                .clickable { updateDropDawnVisible() }
        )
        Box {
            if (visibleState) {
                CustomDropDawnMenu(
                    expanded = visibleState,
                    onDismissRequest = { updateDropDawnVisible() }
                )
            }
        }
    }
}


@Composable
fun StatSection(
    state: ProfileState,
    modifier: Modifier = Modifier,
    onAction: (ProfileAction) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {

        ProfileStat(
            numberText = state.followersCount.toString(),
            text = "подписчики",
            onClick = { onAction(ProfileAction.SubmitGetAllFollowers) },
        )
        ProfileStat(
            numberText = state.friendsCount.toString(),
            text = "друзья",
            onClick = { onAction(ProfileAction.SubmitGetAllFriends) }
        )
        ProfileStat(
            numberText = state.followingCount.toString(),
            text = "подписки",
            onClick = { onAction(ProfileAction.SubmitGetAllFollowing) }
        )

    }
}

@Composable
fun ProfileStat(
    numberText: String,
    text:String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){

    Column(
        modifier = modifier.clickable {
            onClick()
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize =  20.sp
            )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)

    }

}