package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ProvidedValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FSystemUI(
    content: @Composable () -> Unit
) {
    val listValue = ArrayList<ProvidedValue<*>>(2)

    if (LocalStatusBarBrightnessStack.current == null) {
        viewModel<BrightnessStackViewModel>().statusBarBrightnessStack.let { stack ->
            ObserverStatusBarBrightnessStack(stack)
            listValue.add(LocalStatusBarBrightnessStack provides stack)
        }
    }

    if (LocalNavigationBarBrightnessStack.current == null) {
        viewModel<BrightnessStackViewModel>().navigationBarBrightnessStack.let { stack ->
            ObserverNavigationBarBrightnessStack(stack)
            listValue.add(LocalNavigationBarBrightnessStack provides stack)
        }
    }

    if (listValue.isEmpty()) {
        content()
    } else {
        val array = listValue.toTypedArray()
        CompositionLocalProvider(*array) {
            content()
        }
    }
}

internal class BrightnessStackViewModel : ViewModel() {
    val statusBarBrightnessStack = BrightnessStack()
    val navigationBarBrightnessStack = BrightnessStack()
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