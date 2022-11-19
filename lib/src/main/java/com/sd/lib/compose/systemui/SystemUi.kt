package com.sd.lib.compose.systemui

import androidx.compose.runtime.*

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
    val callbackUpdated by rememberUpdatedState(callback)
    return LocalStatusBarBrightnessStack.current
        ?: remember {
            StatusBarBrightnessStack().also {
                callbackUpdated(it)
            }
        }
}

@Composable
private fun navigationBarBrightnessStack(
    callback: (stack: BrightnessStack) -> Unit,
): IBrightnessStack {
    val callbackUpdated by rememberUpdatedState(callback)
    return LocalNavigationBarBrightnessStack.current
        ?: remember {
            NavigationBarBrightnessStack().also {
                callbackUpdated(it)
            }
        }
}