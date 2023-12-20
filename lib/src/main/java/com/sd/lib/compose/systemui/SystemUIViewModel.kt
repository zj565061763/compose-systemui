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
    private var _controller: T? = null

    val brightnessStack: IBrightnessStack = object : BrightnessStack() {
        override fun updateBrightness(brightness: Brightness?) {
            this@SystemUIViewModel.updateBrightness()
        }
    }

    fun registerController(controller: T) {
        _controller = controller
        updateBrightness()
    }

    fun unregisterController(controller: T) {
        if (_controller == controller) {
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