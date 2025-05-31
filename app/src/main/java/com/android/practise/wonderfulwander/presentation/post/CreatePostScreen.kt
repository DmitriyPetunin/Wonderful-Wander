package com.android.practise.wonderfulwander.presentation.post

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.base.action.post.CreatePostAction
import com.example.base.event.post.CreatePostEvent
import com.example.base.state.CreatePostState
import com.example.presentation.viewmodel.CreatePostViewModel


@Composable
fun CreatePostScreenRoute(
    createPostViewModel:CreatePostViewModel,
    navigateToUploadImageScreen: () -> Unit,
//    createPostViewModel: CreatePostViewModel = hiltViewModel()
) {

    val state by createPostViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        createPostViewModel.event.collect{ event->
            when(event){
                CreatePostEvent.NavigateToUploadImageScreen -> {
                    navigateToUploadImageScreen()
                }
            }
        }
    }

    CreatePostScreen(state,createPostViewModel::onAction)
}




@Composable
fun CreatePostScreen(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Создать новый пост",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        //Поле ввода описания
//        OutlinedTextField(
//            value = state.description,
//            onValueChange = { onAction(CreatePostAction.UpdateDescription(it)) },
//            label = { Text("Добавьте описание") },
//            modifier = Modifier.fillMaxWidth(),
//            maxLines = 5,
//            shape = MaterialTheme.shapes.medium
//        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (state.photoUri != Uri.EMPTY) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = state.photoUri,
                        contentDescription = "Выбранное изображение",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                    )

                    Text(
                        text = "Ваше фото",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавить фото",
                        modifier = Modifier.size(80.dp)
                            .clickable {
                                onAction(CreatePostAction.SubmitToAddPhoto)
                            },
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Text(
                        text = "Фото не выбрано",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }

}