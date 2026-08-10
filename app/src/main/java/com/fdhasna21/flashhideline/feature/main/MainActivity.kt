package com.fdhasna21.flashhideline.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fdhasna21.flashhideline.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {
    @Composable
    override fun Content(viewModel: MainViewModel) {
        MainScreen(viewModel = viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }
}