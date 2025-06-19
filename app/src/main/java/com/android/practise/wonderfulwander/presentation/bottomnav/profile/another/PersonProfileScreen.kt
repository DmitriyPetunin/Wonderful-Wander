package com.android.practise.wonderfulwander.presentation.bottomnav.profile.another

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.me.StatSection
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.me.TabScreen
import com.example.base.R
import com.example.base.action.profile.ProfileAction
import com.example.base.state.ProfileState
import com.example.presentation.viewmodel.ProfileViewModel

@Composable
fun PersonProfileScreenRoute(
    userId: String,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val state by profileViewModel.stateProfile.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.onAction(ProfileAction.UpdateUserId(userId = userId))
    }


    PersonProfileScreen(state = state, onAction = profileViewModel::onAction)
}

@Composable
fun PersonProfileScreen(
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

        AnotherTopBar(
            username = state.username,
            isFollowedByUser = state.isFollowedByUser,
            onClickFollowButton = { onAction(ProfileAction.SubmitBellIcon(input = state.userId))},
        )

        Text(
            text = "Profile",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.displayLarge
        )
        if (state.avatarUrl.isNotEmpty()) {
            AsyncImage(
                model = state.avatarUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_visibility_off_foreground),
            )
        }
        if (state.avatarUrl.isNotEmpty()) {
            AsyncImage(
                model = state.avatarUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        StatSection(
            state = state,
            onAction = onAction,
            modifier = Modifier.weight(0.5f)
        )


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
fun AnotherTopBar(
    username: String,
    isFollowedByUser:Boolean,
    modifier: Modifier = Modifier,
    onClickFollowButton: () -> Unit,
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
            painter = painterResource(
                id = if (isFollowedByUser) R.drawable.ic_bell_filled
                else R.drawable.ic_bell
            ),
            contentDescription = "bell",
            modifier = Modifier
                .size(28.dp)
                .clickable { onClickFollowButton() }
        )
    }
}