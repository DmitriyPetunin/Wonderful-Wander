package com.android.practise.wonderfulwander.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import java.io.File


//@Composable
//@Preview
//fun WalkScreen(
//
//) {
//
//    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
//    val context = LocalContext.current
//
//    // файл для сохранения фотографии
//    val photoFile = remember {
//        File.createTempFile("captured_image", ".jpg", context.cacheDir)
//    }
//
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture(),
//        onResult = { result ->
//            if (result) {
//                // Если фото успешно сделано, сохраняем URI файла
//                capturedImageUri = photoFile.toUri()
//            }
//        }
//    )
//
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(
//            onClick = {
//                cameraLauncher.launch(photoFile.toUri())
//            }) {
//            Icon(Icons.Default.Add, contentDescription = "Take Photo")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//
//        capturedImageUri?.let { uri ->
//            AsyncImage(
//                model = uri,
//                contentDescription = "Captured Image",
//                modifier = Modifier
//                    .size(200.dp)
//                    .clip(CircleShape),
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
//}