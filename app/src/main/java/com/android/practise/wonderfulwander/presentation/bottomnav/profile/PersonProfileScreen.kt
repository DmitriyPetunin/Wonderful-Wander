package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.base.action.profile.ProfileAction
import com.example.base.state.ProfileState
import com.example.presentation.viewmodel.ProfileViewModel

@Composable
fun PersonProfileScreenRoute(
    userId:String,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val state by profileViewModel.stateProfile.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.getPersonProfileInfoById(id = userId)
    }


    PersonProfileScreen(state = state)
}

@Composable
fun PersonProfileScreen(
    state:ProfileState
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
            updateDropDawnVisible = {},
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

    }



}