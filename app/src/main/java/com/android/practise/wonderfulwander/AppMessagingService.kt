package com.android.practise.wonderfulwander

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.base.SessionManager
import com.example.navigation.Routes
import com.example.base.R as baseR
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.immutableListOf
import javax.inject.Inject
import kotlin.random.Random

class AppMessagingService :FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TEST-TAG", "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("TEST-TAG", "зашли в onMessageReceived")
        if (message.data.isNotEmpty()) {
            Log.d("TEST-TAG", "data не пуста: ${message.data}")
        } else {
            Log.d("TEST-TAG", "data пуста")
        }
        when (message.data["category"]) {
            "firstCategory" -> handleNotificationCategory(message)
            "secondCategory" -> handleSilentLogCategory(message)
            "thirdCategory" -> handleFeatureRedirectCategory(message)
        }
    }
    private fun handleNotificationCategory(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"] ?: "Default Title"
        val message = remoteMessage.data["body"] ?: "Default Message"

        createHighPriorityNotification(title, message)
    }
    private fun createHighPriorityNotification(title: String, message: String) {
        val channelId = "high_priority_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "High Priority Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for important notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(baseR.drawable.ic_bell)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
    private fun handleSilentLogCategory(remoteMessage: RemoteMessage) {
        val prefs = getSharedPreferences("FCM_Prefs", MODE_PRIVATE)
        prefs.edit().apply {
            putString("last_category", "silent_log")
            putString("data", remoteMessage.data["data"])
            apply()
        }
    }
    private fun handleFeatureRedirectCategory(remoteMessage: RemoteMessage) {
        Log.d("TEST-TAG","handleFeatureRedirectCategory")
        if (isAppInForeground()) {
            val featureName = remoteMessage.data["feature"] ?: return

            if (!listOfFreeUseScreens.none { it == featureName } && isUserAuthenticated()) {
                Log.d("TEST-TAG","ACCESS_DENIED")
                sendBroadcast(
                    Intent("ACCESS_DENIED").apply {
                        putExtra("feature",featureName)
                        setPackage(packageName)
                    }
                )
                return
            } else{
                Log.d("TEST-TAG","NAVIGATE_ACTION")
                sendBroadcast(
                    Intent("NAVIGATE_ACTION").apply {
                        putExtra("feature",featureName)
                        setPackage(packageName)
                    }
                )
            }
        }
    }

    private fun isAppInForeground(): Boolean {
        val appProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(appProcessInfo)
        return appProcessInfo.importance == IMPORTANCE_FOREGROUND
    }
    private fun isUserAuthenticated(): Boolean {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }
    companion object{
        val listOfFreeUseScreens:List<String> = immutableListOf(Routes.REGISTER,Routes.BOTTOM,Routes.UPLOAD_PHOTO)
    }
}