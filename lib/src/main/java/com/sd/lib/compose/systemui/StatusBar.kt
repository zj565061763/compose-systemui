package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf

val LocalStatusBarBrightnessStack = compositionLocalOf { IBrightnessStack.Empty }

@Composable
fun FStatusBarBrightness(brightness: Brightness) {
    val stack = LocalStatusBarBrightnessStack.current
    DisposableEffect(stack, brightness) {
        stack.add(brightness)
        onDispose {
            stack.remove(brightness)
        }
    }
}