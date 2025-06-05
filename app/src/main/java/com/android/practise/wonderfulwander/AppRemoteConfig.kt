package com.android.practise.wonderfulwander

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

object AppRemoteConfig {

    private val remoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        })
        setDefaultsAsync(mapOf(
            "map_feature_enabled" to false
        ))
    }

    suspend fun fetchConfig(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
        } catch (e: Exception) {
            false
        }
    }

    fun isMapEnabled() = remoteConfig.getBoolean("map_feature_enabled")
}