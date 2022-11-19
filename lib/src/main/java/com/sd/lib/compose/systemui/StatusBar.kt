package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalStatusBarBrightnessStack = staticCompositionLocalOf<IBrightnessStack?> { null }

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
    val stack = checkNotNull(LocalStatusBarBrightnessStack.current) {
        "CompositionLocal LocalStatusBarBrightnessStack not present"
    }

    DisposableEffect(stack, brightness) {
        stack.add(brightness)
        onDispose {
            stack.remove(brightness)
        }
    }
}