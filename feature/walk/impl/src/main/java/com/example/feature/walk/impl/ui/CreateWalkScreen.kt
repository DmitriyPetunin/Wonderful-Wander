package com.example.feature.walk.impl.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.R
import com.example.feature.walk.api.action.CreateWalkAction
import com.example.base.model.user.People
import com.example.feature.walk.api.state.CreateWalkState
import com.example.feature.walk.impl.viewmodel.CreateWalkViewModel
import com.example.ui.components.SearchBarCustom
import coil.compose.AsyncImage
import com.example.base.util.showToast
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Point as YandexPoint
import com.example.base.model.walk.Point as MyPoint
import com.yandex.mapkit.geometry.Polygon
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.AnimatedImageProvider

@Composable
fun CreateWalkScreenRoute(
    createWalkViewModel: CreateWalkViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        createWalkViewModel.onAction(CreateWalkAction.GetAllFriends)
    }

    val state by createWalkViewModel.state.collectAsState()

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    } else {
        CreateWalkScreen(state = state, createWalkViewModel::onAction)
    }
}

@Composable
fun CreateWalkScreen(
    state: CreateWalkState,
    onAction: (CreateWalkAction) -> Unit
) {

    val listOfFriends = remember { mutableStateListOf<People>().apply { addAll(state.listOfFriends) } }

    val listOfResult = remember { mutableStateListOf<People>() }


    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ListOfFriends(state = state,onAction,listOfFriends,listOfResult)


        StartPointSection(state = state,onAction)


        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onAction(CreateWalkAction.SubmitSaveWalk) }
        ) {
            Text(text = "отправить приглашения")
        }
    }
}
@Composable
private fun ListOfFriends(
    state:CreateWalkState,
    onAction: (CreateWalkAction) -> Unit,
    listOfFriends:List<People>,
    listOfResult:List<People>
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBarCustom(
                query = state.queryParam,
                items = listOfFriends,
                active = true,
                onActiveChange = {},//TODO
                onQueryChange = { onAction(CreateWalkAction.UpdateQueryParam(it)) },
                searchStringProvider = { it.username },
                itemContent = { person ->
                    FriendSearchListItem(
                        people = person,
                        onResultClick = {
                            onAction(CreateWalkAction.AddFriend(person))
                        }
                    )
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(listOfResult) { friend ->
                FriendListItem(
                    friend = friend,
                )
            }
        }
    }
}

@Composable
fun FriendListItem(
    friend: People,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = friend.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = friend.username,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Готов к прогулке",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Walking",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
private fun FriendSearchListItem(
    people: People,
    onResultClick: () -> Unit
) {

    ListItem(
        headlineContent = { Text(people.username) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier
            .clickable {
                onResultClick()
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    )

}

@Composable
private fun StartPointSection(
    state: CreateWalkState,
    onAction: (CreateWalkAction) -> Unit
) {
    val context = LocalContext.current

    val mapView = remember { MapView(context) }

    val currentCenter = state.point

    val pinsCollection = remember { mapView.mapWindow.map.mapObjects.addCollection() }

    val placemarkTapListener = remember {
        MapObjectTapListener { _, point ->
            context.showToast("Tapped the point (${point.longitude}, ${point.latitude})")
            true
        }
    }

    val animatedIcon = remember {
        AnimatedImageProvider.fromResource(context, R.drawable.animation)
    }

    var lastPlaceMark by remember {
        mutableStateOf(
            pinsCollection.addPlacemark().apply {
                geometry = YandexPoint(currentCenter.latitude, currentCenter.longitude)
                addTapListener(placemarkTapListener)
                useAnimation().apply {
                    setIcon(
                        animatedIcon,
                        IconStyle().apply {
                            scale = 1.5f
                        }
                    )
                }.play()
            }
        )
    }


    val cameraPosition = remember {
        CameraPosition(
            YandexPoint(currentCenter.latitude, currentCenter.longitude), ZOOM, AZIMUTH, TILT
        )
    }

    val inputListener = object : InputListener {
        override fun onMapTap(p0: com.yandex.mapkit.map.Map, p1: YandexPoint) {
            Log.d("MapTap", "onMapTap: ")
        }

        override fun onMapLongTap(map: com.yandex.mapkit.map.Map, point: YandexPoint) {

            pinsCollection.remove(lastPlaceMark)

            lastPlaceMark = pinsCollection.addPlacemark().apply {
                geometry = point
                addTapListener(placemarkTapListener)
                useAnimation().apply {
                    setIcon(
                        animatedIcon,
                        IconStyle().apply {
                            scale = 1.5f
                        }
                    )
                }.play()
            }
            onAction(CreateWalkAction.UpdateStartPoint(MyPoint(point.latitude,point.longitude)))
        }
    }


    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Место сбора:",
            style = MaterialTheme.typography.headlineMedium,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(600.dp)
                .clip(CircleShape.copy(CornerSize(24.dp)))
        ) {
            AndroidView(
                factory = { context ->
                    mapView.apply {
                        mapWindow.map.move(
                            cameraPosition,
                            Animation(Animation.Type.LINEAR, 1.5f),
                            null
                        )
                        mapWindow.map.addInputListener(inputListener)

                        val points = listOf(
                            YandexPoint(59.9343, 30.3351),
                            YandexPoint(55.7558, 37.6176),
                            YandexPoint(55.78874,49.12214)
                        )
                        val polyline = Polyline(points)
                        val polylineObject = mapWindow.map.mapObjects.addPolyline(polyline)


                        val polygon = Polygon(LinearRing(points), emptyList())
                        val polygonMapObject = mapWindow.map.mapObjects.addPolygon(polygon)

                    }
                },
                modifier = Modifier.fillMaxSize(),

                update = { mapView ->
                    Log.d("UPDATE", "UPDATE StartPointSection ")
                }
            )

            ButtonsColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                mapView = mapView
            )
        }
    }
}

@Composable
fun ButtonsColumn(
    mapView: MapView,
    modifier: Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Button(
            onClick = {
                mapView.let {
                    changeZoomByStep(
                        mapView = it,
                        value = ZOOM_STEP
                    )
                }
            },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Button(
            onClick = {
                mapView.let {
                    changeZoomByStep(
                        mapView = it,
                        value = -ZOOM_STEP
                    )
                }
            },
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
fun changeZoomByStep(mapView: MapView, value: Float) {
    with(mapView.mapWindow.map.cameraPosition) {
        mapView.mapWindow.map.move(
            CameraPosition(target, zoom + value, azimuth, tilt),
            Animation(com.yandex.mapkit.Animation.Type.SMOOTH, 0.5f),
            null,
        )
    }
}

private const val ZOOM_STEP = 1f

private const val ZOOM = 17.0f

private const val AZIMUTH = 0.0f

private const val TILT = 30.0f
