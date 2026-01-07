package com.android.practise.wonderfulwander.app

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import com.android.practise.wonderfulwander.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application(){
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }
}