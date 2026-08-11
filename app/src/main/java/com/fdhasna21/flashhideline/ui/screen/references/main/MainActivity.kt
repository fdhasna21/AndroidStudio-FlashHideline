package com.fdhasna21.flashhideline.ui.screen.references.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.ui.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FlashHidelineTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}