package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf

val LocalNavigationBarBrightnessStack = compositionLocalOf { IBrightnessStack.Empty }

@Composable
fun FNavigationBarBrightness(brightness: Brightness) {
    val stack = LocalNavigationBarBrightnessStack.current
    DisposableEffect(stack, brightness) {
        stack.add(brightness)
        onDispose {
            stack.remove(brightness)
        }
    }
}