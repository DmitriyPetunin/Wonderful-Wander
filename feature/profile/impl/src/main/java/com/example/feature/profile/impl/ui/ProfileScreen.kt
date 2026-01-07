package com.example.feature.profile.impl.ui

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.example.base.R
import com.example.feature.profile.api.action.ProfileAction
import com.example.base.enums.PeopleEnum
import com.example.feature.profile.api.event.ProfileEvent
import com.example.feature.profile.api.state.ProfileState
import com.example.feature.profile.impl.viewmodel.ProfileViewModel
import com.example.base.R as baseR

@Composable
fun ProfileScreenRoute(
    navigateToAuthScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    navigateToPeopleScreen: (PeopleEnum) -> Unit,
    navigateToUpdateScreen: () -> Unit,
    navigateToPostDetailInfoScreen: (String) -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    val state by profileViewModel.stateProfile.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.onAction(ProfileAction.Init)
    }

    LaunchedEffect(Unit) {
        profileViewModel.event.collect { event ->
            when (event) {
                is ProfileEvent.NavigateToFriendsPage -> {
                    navigateToPeopleScreen(PeopleEnum.FRIENDS)
                    Toast.makeText(context, "friends", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToAuthPage -> {
                    navigateToAuthScreen()
                    Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToFollowersPage -> {
                    navigateToPeopleScreen(PeopleEnum.FOLLOWERS)
                    Toast.makeText(context, "followers", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToFollowingPage -> {
                    navigateToPeopleScreen(PeopleEnum.FOLLOWING)
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

                is ProfileEvent.NavigateToRegisterPage -> {
                    navigateToRegisterScreen()
                    Toast.makeText(context, "NavigateToRegisterPage", Toast.LENGTH_SHORT).show()
                }
                is ProfileEvent.NavigateToPostDetail -> {
                    navigateToPostDetailInfoScreen(event.postId)
                    Toast.makeText(context, "NavigateToPostDetail", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.DeletePost -> {
                    Toast.makeText(context, "пост с id ${event.postId} был удалён", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.SavePost -> {
                    Toast.makeText(context, "пост с id ${event.postId} был сохранён", Toast.LENGTH_SHORT).show()
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

        MeTopBar(
            username = state.username,
            modifier = Modifier.fillMaxWidth(),
            updateDropDawnVisible = { onAction(ProfileAction.UpdateDropDawnVisible(isVisible = !state.dropDownMenuVisible))},
            visibleState = state.dropDownMenuVisible,
            onAction = onAction
        )

        Text(
            text = "Profile",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.displayLarge
        )
        CustomAvatar(
            avatarUrl = state.avatarUrl,
            onAction = onAction
        )
        if (state.username.isNotEmpty()) {
            Text(
                text = state.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        StatSection(state = state,onAction = onAction, modifier = Modifier.weight(0.5f))


        TabScreen(
            state = state,
            modifier = Modifier.weight(1.5f),
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = { index -> onAction(ProfileAction.UpdateSelectedTab(index)) },
            onAction = onAction
        )
    }
}


@Composable
fun MeTopBar(
    username: String,
    modifier: Modifier = Modifier,
    updateDropDawnVisible: () -> Unit,
    visibleState:Boolean,
    onAction: (ProfileAction) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = username,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .padding(horizontal = 48.dp)
        )
        Icon(
            painter = painterResource(id = baseR.drawable.ic_dotmenu),
            contentDescription = "menu",
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .clickable { updateDropDawnVisible() }
        )
        // Выпадающее меню
        Box(
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            CustomDropDawnMenu(
                expanded = visibleState,
                onDismissRequest = { updateDropDawnVisible() },
                onAction = onAction
            )
        }
    }
}




@Composable
fun CustomAvatar(
    avatarUrl: String,
    onAction: (ProfileAction) -> Unit
){

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) {
            //закрыли галлерию
            Log.d("PickImage", "No image selected")
        } else {
            onAction(ProfileAction.SubmitUploadAvatar(uri))
        }
    }

    if (avatarUrl.isNotEmpty()) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_visibility_off_foreground),
        )
    } else {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Default profile icon",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .clickable { pickImageLauncher.launch("image/*") }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
