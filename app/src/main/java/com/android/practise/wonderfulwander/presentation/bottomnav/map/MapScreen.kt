package com.android.practise.wonderfulwander.presentation.bottomnav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.base.action.geo.GeoAction
import com.example.base.state.GeoState
import com.example.presentation.viewmodel.GeoViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MapScreenRoute(
    geoViewModel: GeoViewModel = hiltViewModel()
) {
    val mapScreenState by geoViewModel.geoState.collectAsState()

    MapScreen(state = mapScreenState, geoViewModel::onAction)
}

@Composable
fun MapScreen(
    state: GeoState,
    onAction: (GeoAction) -> Unit
) {
    val mapView = remember { MutableStateFlow<MapView?>(null) }

    val currentCenter = state.point

    var counterState by remember { mutableStateOf(0) }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    mapView.value = this
                }
            }, modifier = Modifier.fillMaxSize(),

            update = { mapView ->

                mapView.mapWindow.map.move(
                    CameraPosition(
                        Point(currentCenter.latitude, currentCenter.longitude), ZOOM, AZIMUTH, TILT
                    ), Animation(Animation.Type.SMOOTH, 1.5f), null
                )

//                mapView.mapWindow.map.addCameraListener { map, cameraPosition, cameraUpdateReason, isFinished ->
//
//                    if (isFinished) {
//                        Log.d("TEST-TAG","зашли в метод")
//                        onAction(
//                            GeoAction.UpdateCurrentCenter(
//                                longitude = cameraPosition.target.longitude,
//                                latitude = cameraPosition.target.latitude
//                            )
//                        )
//                    }
//                }
            }
        )
        Text(
            text = state.text,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.End,
        ) {
            Button(
                onClick = {
                    mapView.value?.let {
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
                    mapView.value?.let {
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

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 24.dp, end = 12.dp)
        ) {
            Text(
                text = "Создать прогулку"
            )
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            counterState += 1
        }
    }

    LaunchedEffect(counterState) {

        if (counterState != 0){
            val newCenter = (mapView.value?.mapWindow?.map?.cameraPosition?.target ?: currentCenter) as Point

            //Log.d("TEST-TAG", "newCenter = ${newCenter.latitude} + ${newCenter.longitude} ")

            onAction(
                GeoAction.UpdateCurrentCenter(
                    longitude = newCenter.longitude,
                    latitude = newCenter.latitude)
            )
            onAction(GeoAction.UpdateText)
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

private const val AZIMUTH = 150.0f

private const val TILT = 00.0f