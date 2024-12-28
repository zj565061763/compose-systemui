package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

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
  val stack = navigationBarViewModel().brightnessStack
  DisposableEffect(stack, brightness) {
    stack.add(brightness)
    onDispose {
      stack.remove(brightness)
    }
  }
}