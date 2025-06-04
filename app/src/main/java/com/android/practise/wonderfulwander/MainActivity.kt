package com.android.practise.wonderfulwander


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.practise.wonderfulwander.presentation.main.MainScreen
import com.example.compose.WonderfulWanderTheme
import com.example.presentation.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseCrashlytics: FirebaseCrashlytics

    private val navigationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "NAVIGATE_ACTION" -> {
                    val featureName = intent.getStringExtra("feature") ?: return
                    mainViewModel.tryNavigate(featureName)
                }

                "ACCESS_DENIED" -> {
                    val featureName = intent.getStringExtra("feature") ?: return
                    mainViewModel.accessDenied(featureName)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)
        requestNotificationPermission()


        setContent {
            WonderfulWanderTheme {
                Surface() {
                    MainScreen(viewModel = mainViewModel)
                }
            }
        }

        val user = firebaseAuth.currentUser
        val id = user?.uid
        firebaseCrashlytics.setCustomKeys {
            key("userID", id.orEmpty())
        }

//        lifecycleScope.launch {
//            delay(3000L)
//            throw RuntimeException("аххахахах drop table")
//        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        registerReceiver(
            navigationReceiver,
            IntentFilter(
                IntentFilter().apply {
                    addAction("NAVIGATE_ACTION")
                    addAction("ACCESS_DENIED")
                }),
            RECEIVER_NOT_EXPORTED
        )
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        unregisterReceiver(navigationReceiver)

    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}