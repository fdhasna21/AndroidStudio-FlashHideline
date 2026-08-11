package com.fdhasna21.flashhideline.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fdhasna21.flashhideline.core.utils.Constants
import com.fdhasna21.flashhideline.ui.screen.main.MainScreen
import com.fdhasna21.flashhideline.ui.webview.WebViewScreen

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Main.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = Screen.WebView.route,
            arguments = listOf(
                navArgument(Constants.EXTRA.ENCODED_URL) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawUrl = backStackEntry.arguments?.getString(Constants.EXTRA.ENCODED_URL) ?: ""
            val targetUrl = Uri.decode(rawUrl).ifBlank { Constants.DEFAULT.URL }

            WebViewScreen(
                url = targetUrl,
                viewModel =  hiltViewModel(),
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}