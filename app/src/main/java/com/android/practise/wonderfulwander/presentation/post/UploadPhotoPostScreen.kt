package com.android.practise.wonderfulwander.presentation.post

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.base.action.post.CreatePostAction
import com.example.base.state.CreatePostState
import com.example.base.state.PhotoState
import com.example.presentation.viewmodel.CreatePostViewModel


@Composable
fun UploadPhotoPostScreenRoute(
    createPostViewModel: CreatePostViewModel
) {

    val state by createPostViewModel.state.collectAsState()


    UploadPhotoPostScreen(state, createPostViewModel::onAction)

}

@Composable
fun UploadPhotoPostScreen(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) {
            //закрыли галлерию
            Log.d("PickImage", "No image selected")
        } else {
            onAction(CreatePostAction.UpdatePhotoUri(uri))
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "загрузить фото",
            style = MaterialTheme.typography.headlineMedium
        )
        when (state.photoState) {
            is PhotoState.Init -> {
                CustomBox {
                    Text(text = "здесь будет ваше фото")
                }
            }

            is PhotoState.Loading -> {
                CustomBox {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 4.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            is PhotoState.Success -> {
                CustomBox {
                    AsyncImage(
                        model = state.photoUri,
                        contentDescription = "photo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Button(
            onClick = { pickImageLauncher.launch("image/*") }
        ) {
            Text(text = "загрузить фото")
        }
    }
}