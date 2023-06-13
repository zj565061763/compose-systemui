package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FStatusBarLight() {
    val brightness = remember { Brightness.light() }
    StatusBarBrightness(brightness)
}

@Composable
fun FStatusBarDark() {
    val brightness = remember { Brightness.dark() }
    StatusBarBrightness(brightness)
}

@Composable
private fun StatusBarBrightness(brightness: Brightness) {
    val stack = statusBarBrightnessStack()
    DisposableEffect(stack, brightness) {
        stack.add(brightness)
        onDispose {
            stack.remove(brightness)
        }
    }
}

@Composable
private fun statusBarBrightnessStack(): IBrightnessStack {
    return viewModel<BrightnessStackViewModel>().statusBarBrightnessStack
}