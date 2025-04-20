package com.android.practise.wonderfulwander.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.practise.service.GeoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GeoViewModel @Inject constructor(
    val geoService: GeoService
):ViewModel() {

    private val _text = MutableStateFlow<String>("55.78874, 49.12214")
    val text = _text.asStateFlow()

    fun getText(string: String){
        viewModelScope.launch {
            val response = geoService.getGeoByCoordinates(geocode = string)
            val text = response.response.GeoObjectCollection.featureMember.get(0).GeoObject.metaDataProperty.GeocoderMetaData.text

            _text.value = text
        }
    }
}