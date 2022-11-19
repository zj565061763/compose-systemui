package com.sd.lib.compose.systemui

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FSystemUi(
    content: @Composable () -> Unit
) {
    val statusBarBrightnessStack = LocalStatusBarBrightnessStack.current
        ?: viewModel<BrightnessStackViewModel>().statusBarBrightnessStack.also {
            ObserverStatusBarBrightnessStack(it)
        }

    val navigationBarBrightnessStack = LocalNavigationBarBrightnessStack.current
        ?: viewModel<BrightnessStackViewModel>().navigationBarBrightnessStack.also {
            ObserverNavigationBarBrightnessStack(it)
        }

    CompositionLocalProvider(
        LocalStatusBarBrightnessStack provides statusBarBrightnessStack,
        LocalNavigationBarBrightnessStack provides navigationBarBrightnessStack,
    ) {
        content()
    }
}

fun androidx.activity.ComponentActivity.statusBarBrightnessStack(): IBrightnessStack {
    val viewModel by viewModels<BrightnessStackViewModel>()
    return viewModel.statusBarBrightnessStack
}

fun androidx.activity.ComponentActivity.navigationBarBrightnessStack(): IBrightnessStack {
    val viewModel by viewModels<BrightnessStackViewModel>()
    return viewModel.navigationBarBrightnessStack
}

@Composable
private fun ObserverStatusBarBrightnessStack(stack: BrightnessStack) {
    val statusBarController = rememberStatusBarController()
    DisposableEffect(statusBarController, stack) {
        val callback = BrightnessStack.Callback { brightness ->
            if (brightness != null) {
                statusBarController.isLight = brightness is Brightness.Light
            }
        }
        stack.registerCallback(callback)
        onDispose {
            stack.unregisterCallback(callback)
        }
    }
}

@Composable
private fun ObserverNavigationBarBrightnessStack(stack: BrightnessStack) {
    val navigationBarController = rememberNavigationBarController()
    DisposableEffect(navigationBarController, stack) {
        val callback = BrightnessStack.Callback { brightness ->
            if (brightness != null) {
                navigationBarController.isLight = brightness is Brightness.Light
            }
        }
        stack.registerCallback(callback)
        onDispose {
            stack.unregisterCallback(callback)
        }
    }
}

internal class BrightnessStackViewModel : ViewModel() {
    val statusBarBrightnessStack = BrightnessStack()
    val navigationBarBrightnessStack = BrightnessStack()
}