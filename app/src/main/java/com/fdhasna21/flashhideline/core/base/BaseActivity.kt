package com.fdhasna21.flashhideline.core.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.Constants
import java.lang.reflect.ParameterizedType

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

abstract class BaseActivity<VM : BaseViewModel<*>> : ComponentActivity() {

    lateinit var viewModel: VM
        private set

    @Composable
    abstract fun Content(viewModel: VM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[getViewModelClass()]

        onSetupData()

        setContent {
            FlashHidelineTheme {
                Content(viewModel = viewModel)
            }
        }
    }

    open fun onSetupData() {}

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<VM> {
        val type = javaClass.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[0] as Class<VM>
    }
}