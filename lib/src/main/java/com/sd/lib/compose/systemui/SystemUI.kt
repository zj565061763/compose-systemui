package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalStatusBarController = staticCompositionLocalOf<IStatusBarController?> { null }
internal val LocalNavigationBarController = staticCompositionLocalOf<INavigationBarController?> { null }

@Composable
fun FSystemUI(
  content: @Composable () -> Unit,
) {
  run {
    val localStatusBarController = LocalStatusBarController.current
    val localNavigationBarController = LocalNavigationBarController.current
    if (localStatusBarController != null || localNavigationBarController != null) {
      checkNotNull(localStatusBarController)
      checkNotNull(localNavigationBarController)
      content()
      return
    }
  }

  val statusBarController = run {
    val viewModel = statusBarViewModel()
    val controller = rememberStatusBarController()
    DisposableEffect(viewModel, controller) {
      viewModel.registerController(controller)
      onDispose {
        viewModel.unregisterController(controller)
      }
    }
    controller
  }

  val navigationBarController = run {
    val viewModel = navigationBarViewModel()
    val controller = rememberNavigationBarController()
    DisposableEffect(viewModel, controller) {
      viewModel.registerController(controller)
      onDispose {
        viewModel.unregisterController(controller)
      }
    }
    controller
  }

  CompositionLocalProvider(
    LocalStatusBarController provides statusBarController,
    LocalNavigationBarController provides navigationBarController,
  ) {
    content()
  }
}

@Composable
fun fStatusBarController(): IStatusBarController {
  return checkNotNull(LocalStatusBarController.current) { "Not in FSystemUI scope." }
}

@Composable
fun fNavigationBarController(): INavigationBarController {
  return checkNotNull(LocalNavigationBarController.current) { "Not in FSystemUI scope." }
}