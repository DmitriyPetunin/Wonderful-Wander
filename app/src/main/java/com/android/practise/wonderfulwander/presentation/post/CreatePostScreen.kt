package com.android.practise.wonderfulwander.presentation.post

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.practise.wonderfulwander.presentation.walk.SearchBarCustom
import com.example.base.action.post.CreatePostAction
import com.example.base.model.post.category.Category
import com.example.base.state.CreatePostState
import com.example.presentation.viewmodel.CreatePostViewModel


@Composable
fun CreatePostScreenRoute(
    createPostViewModel: CreatePostViewModel = hiltViewModel()
) {

    val state by createPostViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        createPostViewModel.onAction(CreatePostAction.Init)
    }

    LaunchedEffect(Unit) {
        createPostViewModel.event.collect { event ->
            when (event) {

                else -> {}
            }
        }
    }
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    } else {
        CreatePostScreen(state, createPostViewModel::onAction)
    }
}


@Composable
fun CreatePostScreen(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {

    //val selectedCategory by remember { mutableStateOf(state.selectedCategory) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Создать новый пост",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 1. Секция выбора категории
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Выберите одну категорию",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Поле для отображения выбранной категории
            if (state.selectedCategory != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = state.selectedCategory!!.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                SearchBarCustom(
                    query = state.queryParam,
                    active = state.searchBarIsActive,
                    onActiveChange = { value ->  onAction(CreatePostAction.UpdateActiveParam(active = value)) },
                    items = state.listOfCategories,
                    onQueryChange = { onAction(CreatePostAction.UpdateQueryParam(it)) },
                    searchStringProvider = { it.name },
                    itemContent = { category ->
                        CategoryItem(
                            category = category,
                            onResultClick = {
                                onAction(CreatePostAction.UpdateSelectedCategory(category))
                                onAction(CreatePostAction.UpdateActiveParam(active = false))
                            }
                        )
                    }
                )
            }
            if (state.selectedCategory != null) {
                TextButton(
                    onClick = {
                        onAction(CreatePostAction.UpdateSelectedCategory(null))
                        onAction(CreatePostAction.UpdateActiveParam(active = true))
                              },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Изменить категорию")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Секция загрузки фото
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            UploadPhotoPostScreen(
                state = state,
                onAction = onAction,
                modifier = Modifier.size(280.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Секция описания
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Описание",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            OutlinedTextField(
                value = state.title,
                onValueChange = { onAction(CreatePostAction.UpdateTitle(it)) },
                label = { Text("Добавьте описание") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                shape = MaterialTheme.shapes.medium
            )
        }
    }
}

@Composable
private fun CategoryItem(
    category: Category,
    onResultClick: () -> Unit,
){

    ListItem(
        headlineContent = { Text(text = category.name) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier
            .clickable { onResultClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    )
}