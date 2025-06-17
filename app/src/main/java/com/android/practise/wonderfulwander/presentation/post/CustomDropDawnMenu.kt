package com.android.practise.wonderfulwander.presentation.post
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.base.action.profile.ProfileAction


@Composable
fun CustomDropDawnMenu(
    expanded:Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onAction:(ProfileAction) -> Unit,
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .width(IntrinsicSize.Min),
        offset = DpOffset(x = 0.dp, y = 8.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        DropdownMenuItem(
            text = { Text("выйти") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = {
                onAction(ProfileAction.SignOut)
            },
        )
        DropdownMenuItem(
            text = { Text("изменить") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = {
                onAction(ProfileAction.SubmitUpdateProfileInfo)
            }
        )
        DropdownMenuItem(
            text = { Text("удалить") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = {
                onAction(ProfileAction.SubmitDeleteProfile)
            }
        )
    }
}