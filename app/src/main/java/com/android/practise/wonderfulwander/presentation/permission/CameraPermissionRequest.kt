package com.android.practise.wonderfulwander.presentation.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.practise.wonderfulwander.presentation.walk.PermissionDeniedDialog

@Composable
fun CameraPermissionRequest(
    onPermissionGranted: () -> Unit, // Колбэк при успешном разрешении,
    permissionDeniedContent: () -> Unit
) {
    val cameraPermission = android.Manifest.permission.CAMERA

    var showPermissionDeniedDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val shouldShowRationale = remember {
        ActivityCompat.shouldShowRequestPermissionRationale(
            context as Activity,
            cameraPermission
        )
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            //уже есть доступ камере
        } else {
            if (shouldShowRationale) {
                // Показываем объяснение (например, Snackbar)
                // Можно добавить кнопку "Запросить снова"
            } else {
                // Пользователь отказал навсегда → показываем диалог
                showPermissionDeniedDialog = true
            }
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, cameraPermission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGranted()
        } else {
            permissionLauncher.launch(cameraPermission)
        }
    }


    if (showPermissionDeniedDialog) {
        PermissionDeniedDialog(
            onDismiss = { showPermissionDeniedDialog = false },
            onGoToSettings = {
                // Открываем настройки, чтобы пользователь мог дать разрешение вручную
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
                showPermissionDeniedDialog = false
            }
        )
    }

    // 4. Контент, если разрешение отклонено (например, серый экран с кнопкой "Повторить")
    if (ContextCompat.checkSelfPermission(
            context,
            cameraPermission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        permissionDeniedContent()
    }


}