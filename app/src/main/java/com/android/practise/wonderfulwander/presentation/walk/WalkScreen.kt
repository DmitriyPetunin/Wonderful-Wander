package com.android.practise.wonderfulwander.presentation.walk

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.practise.wonderfulwander.presentation.permission.CameraPermissionRequest
import com.example.base.action.walk.WalkAction
import com.example.base.state.WalkState
import com.example.presentation.viewmodel.WalkViewModel
import java.io.File

@Composable
fun WalkScreenRoute(
    walkViewModel: WalkViewModel = hiltViewModel(),
) {
    val state by walkViewModel.state.collectAsState()

    WalkScreen(state = state, walkViewModel::onAction)
}

@Composable
fun WalkScreen(
    state: WalkState,
    onAction: (WalkAction) -> Unit
) {
    val hasCameraPermission = state.hasCameraPermission

    CameraPermissionRequest(
        onPermissionGranted = { onAction(WalkAction.UpdateCameraPermission(isGranted = true)) },
        permissionDeniedContent = {
            //повторный запрос к разрешению
        },
    )

    if (hasCameraPermission) {
        WalkScreenContent()
    }
}

@Composable
fun PermissionDeniedDialog(onDismiss: () -> Unit, onGoToSettings: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Доступ к камере отклонён") },
        text = { Text("Для работы с камерой необходимо разрешение. Перейдите в настройки?") },
        confirmButton = {
            TextButton(onClick = { onGoToSettings() }) {
                Text("Settings")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun WalkScreenContent() {
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Create a temporary file for the captured image
    val photoFile = remember {
        File.createTempFile("captured_image", ".jpg", context.cacheDir)
    }

    // Generate a content URI using FileProvider
    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
    }

    // Launcher for taking a picture
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                capturedImageUri = photoUri
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                cameraLauncher.launch(photoUri)
            }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Take Photo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        capturedImageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Captured Image",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}