package com.android.practise.wonderfulwander.presentation.bottomnav.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.action.profile.UpdateProfileAction
import com.example.base.event.profile.ProfileEvent
import com.example.base.state.UpdateProfileState
import com.example.presentation.viewmodel.ProfileViewModel


@Composable
fun UpdateProfileScreenRoute(
    navigateToProfile: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        profileViewModel.initUpdateState()
    }

    LaunchedEffect(Unit) {
        profileViewModel.event.collect{event->
            when(event){
                is ProfileEvent.UpdateProfileInfo -> {
                    navigateToProfile()
                }
                else -> {

                }
            }
        }
    }

    val updateState by profileViewModel.stateUpdateProfile.collectAsState()

    UpdateProfileScreen(state = updateState,profileViewModel::onActionUpdate)
}

@Composable
fun UpdateProfileScreen(
    state: UpdateProfileState,
    onAction: (UpdateProfileAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Update Profile",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.displayLarge
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = {onAction(UpdateProfileAction.UpdateEmailField(it))}
        )


        OutlinedTextField(
            value = state.firstName,
            onValueChange = {onAction(UpdateProfileAction.UpdateFirstNameField(it))}
        )

        OutlinedTextField(
            value = state.lastName,
            onValueChange = {onAction(UpdateProfileAction.UpdateLastNameField(it))}
        )

        OutlinedTextField(
            value = state.bio,
            onValueChange = {onAction(UpdateProfileAction.UpdateBioField(it))}
        )

        OutlinedTextField(
            value = state.photoVisibility,
            onValueChange = {onAction(UpdateProfileAction.UpdatePhotoVisibilityField(it))}
        )


        OutlinedTextField(
            value = state.walkVisibility,
            onValueChange = {onAction(UpdateProfileAction.UpdateWalkVisibilityField(it))}
        )


        Button(
            onClick = {
                onAction(UpdateProfileAction.SubmitSaveButton)
            }
        ) {
            Text("изменить данные и перейти назад в profile")
        }
    }


}