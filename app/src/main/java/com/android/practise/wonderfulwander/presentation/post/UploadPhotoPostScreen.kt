package com.android.practise.wonderfulwander.presentation.post

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun UploadPhotoPostScreen(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit,
    modifier: Modifier
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
        modifier = Modifier.size(500.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "загрузить фото",
            style = MaterialTheme.typography.headlineMedium
        )
        when (state.photoState) {
            is PhotoState.Init -> {
                CustomBox{
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

@Composable
fun CustomBox(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width =  1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ){
        content()
    }

}