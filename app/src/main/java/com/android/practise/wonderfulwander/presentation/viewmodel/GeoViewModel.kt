package com.android.practise.wonderfulwander.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.practise.service.GeoService
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GeoViewModel @Inject constructor(
    val geoService: GeoService,
    val firebaseAnalytics: FirebaseAnalytics
):ViewModel() {

    private val _text = MutableStateFlow("55.78874, 49.12214")
    val text = _text.asStateFlow()

    fun getText(string: String){
        viewModelScope.launch {

            firebaseAnalytics.logEvent("api_call"){
                param("endpoint", "v1/")
                param("geocode", string)
            }
            val response = geoService.fetchGeoData(geocode = string)
            val text = response.response.GeoObjectCollection.featureMember.get(0).GeoObject.metaDataProperty.GeocoderMetaData.text

            _text.value = text
        }
    }
}