package com.android.practise.wonderfulwander

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class DemoAppMessagingService:FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TEST-TAG", "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }
}