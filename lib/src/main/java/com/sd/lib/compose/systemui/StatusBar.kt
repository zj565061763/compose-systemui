package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

val LocalStatusBarBrightnessStack = compositionLocalOf { IBrightnessStack.Empty }

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
    val stack = LocalStatusBarBrightnessStack.current
    DisposableEffect(stack, brightness) {
        stack.add(brightness)
        onDispose {
            stack.remove(brightness)
        }
    }
}