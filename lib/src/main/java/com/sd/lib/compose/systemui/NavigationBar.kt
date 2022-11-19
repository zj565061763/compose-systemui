package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

val LocalNavigationBarBrightnessStack = compositionLocalOf { IBrightnessStack.Empty }

@Composable
fun FNavigationBarLight() {
    val brightness = remember { Brightness.light() }
    NavigationBarBrightness(brightness)
}

@Composable
fun FNavigationBarDark() {
    val brightness = remember { Brightness.dark() }
    NavigationBarBrightness(brightness)
}

@Composable
private fun NavigationBarBrightness(brightness: Brightness) {
    val stack = LocalNavigationBarBrightnessStack.current
    DisposableEffect(stack, brightness) {
        stack.add(brightness)
        onDispose {
            stack.remove(brightness)
        }
    }
}