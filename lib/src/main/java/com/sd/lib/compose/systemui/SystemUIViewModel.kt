package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun statusBarViewModel(): StatusBarViewModel {
  return viewModel<StatusBarViewModel>()
}

@Composable
internal fun navigationBarViewModel(): NavigationBarViewModel {
  return viewModel<NavigationBarViewModel>()
}

internal class StatusBarViewModel : SystemUIViewModel<IStatusBarController>()

internal class NavigationBarViewModel : SystemUIViewModel<INavigationBarController>()

internal abstract class SystemUIViewModel<T : ISystemUIController> : ViewModel() {
  @Volatile
  private var _controller: T? = null

  val brightnessStack: IBrightnessStack = object : BrightnessStack() {
    override fun onBrightnessChanged(brightness: Brightness?) {
      updateBrightness()
    }
  }

  fun getController(): T? = _controller

  fun registerController(controller: T) {
    // controller对象只要存在就可以正常工作，不考虑并发
    if (_controller == null) {
      _controller = controller
      updateBrightness()
    }
  }

  fun unregisterController(controller: T) {
    if (_controller === controller) {
      _controller = null
    }
  }

  private fun updateBrightness() {
    val controller = _controller ?: return
    val brightness = brightnessStack.last() ?: return
    controller.isLight = brightness is Brightness.Light
  }

  override fun onCleared() {
    super.onCleared()
    _controller = null
  }
}