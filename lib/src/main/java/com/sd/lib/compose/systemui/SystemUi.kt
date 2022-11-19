package com.sd.lib.compose.systemui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FSystemUi(
    content: @Composable () -> Unit
) {
    var statusBarStack by remember { mutableStateOf<BrightnessStack?>(null) }
    val statusBarBrightnessStack = statusBarBrightnessStack { statusBarStack = it }

    val statusBarController = rememberStatusBarController()
    statusBarStack?.let { stack ->
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


    var navigationBarStack by remember { mutableStateOf<BrightnessStack?>(null) }
    val navigationBarBrightnessStack = navigationBarBrightnessStack { navigationBarStack = it }

    val navigationBarController = rememberNavigationBarController()
    navigationBarStack?.let { stack ->
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

    CompositionLocalProvider(
        LocalStatusBarBrightnessStack provides statusBarBrightnessStack,
        LocalNavigationBarBrightnessStack provides navigationBarBrightnessStack,
    ) {
        content()
    }
}

@Composable
private fun statusBarBrightnessStack(
    callback: (stack: BrightnessStack) -> Unit,
): IBrightnessStack {
    val stack = LocalStatusBarBrightnessStack.current
    if (stack != null) return stack
    return viewModel<BrightnessStackViewModel>().statusBarBrightnessStack.also {
        callback(it)
    }
}

@Composable
private fun navigationBarBrightnessStack(
    callback: (stack: BrightnessStack) -> Unit,
): IBrightnessStack {
    val stack = LocalNavigationBarBrightnessStack.current
    if (stack != null) return stack
    return viewModel<BrightnessStackViewModel>().navigationBarBrightnessStack.also {
        callback(it)
    }
}

internal class BrightnessStackViewModel : ViewModel() {
    val statusBarBrightnessStack = BrightnessStack()
    val navigationBarBrightnessStack = BrightnessStack()
}