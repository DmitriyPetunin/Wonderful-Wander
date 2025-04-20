package com.android.practise.wonderfulwander.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.practise.wonderfulwander.presentation.viewmodel.GeoViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@Composable
fun MapScreen (
    geoViewModel: GeoViewModel = hiltViewModel()
) {
    val mapView = remember { MutableStateFlow<MapView?>(null) }

    val context = LocalContext.current

    val location = remember { mutableStateOf(Point(55.78874, 49.12214)) } // Тукая
    var currentCenter by remember { mutableStateOf(location.value) }

    val locationText by geoViewModel.text.collectAsState()


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    mapView.value = this
                }
            },
            modifier = Modifier.fillMaxSize(),

            update = { mapView ->

                mapView.map.move(
                    CameraPosition(
                        location.value,
                        17.0f,
                        150.0f,
                        00.0f
                    ),
                    Animation(Animation.Type.SMOOTH, 1.5f),
                    null
                )
                Log.d("Test-Tag","совершили переход в точку ${location.value.latitude} ${location.value.longitude}")

                mapView.map.addCameraListener(object : CameraListener {
                    override fun onCameraPositionChanged(
                        map: Map,
                        cameraPosition: CameraPosition,
                        cameraUpdateReason: CameraUpdateReason,
                        isFinished: Boolean
                    ) {
                        if (isFinished) {
                            currentCenter = cameraPosition.target
                        }
                    }
                })
            }
        )
        Text(
            text = locationText,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
        )
        LaunchedEffect(Unit) {
            while (true) {
                delay(5000) // Задержка в 5 секунд
                val newCenter = currentCenter

                geoViewModel.getText("${newCenter.latitude},${newCenter.longitude}")

                // Если нужно, можно также обновить состояние location
                //location.value = newCenter
            }
        }
    }

}
