package com.sd.lib.compose.systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

internal val LocalStatusBarController = staticCompositionLocalOf<IStatusBarController?> { null }
internal val LocalNavigationBarController = staticCompositionLocalOf<INavigationBarController?> { null }

@Composable
fun FSystemUI(
    content: @Composable () -> Unit
) {
    if (LocalStatusBarController.current != null || LocalNavigationBarController.current != null) {
        checkNotNull(LocalStatusBarController.current)
        checkNotNull(LocalNavigationBarController.current)
        content()
        return
    }

    val stackViewModel = viewModel<BrightnessStackViewModel>()

    val statusBarController = rememberStatusBarController().also { controller ->
        DisposableEffect(stackViewModel, controller) {
            stackViewModel.registerStatusBarController(controller)
            onDispose {
                stackViewModel.unregisterStatusBarController(controller)
            }
        }
    }

    val navigationBarController = rememberNavigationBarController().also { controller ->
        DisposableEffect(stackViewModel, controller) {
            stackViewModel.registerNavigationBarController(controller)
            onDispose {
                stackViewModel.unregisterNavigationBarController(controller)
            }
        }
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
    return checkNotNull(LocalStatusBarController.current) { "This should be used in FSystemUI" }
}

@Composable
fun fNavigationBarController(): INavigationBarController {
    return checkNotNull(LocalNavigationBarController.current) { "This should be used in FSystemUI" }
}

internal class BrightnessStackViewModel : ViewModel() {
    private var _statusBarController: IStatusBarController? = null
    private var _navigationBarController: INavigationBarController? = null

    private val _statusBarBrightnessStack = BrightnessStack().apply {
        registerCallback { brightness ->
            _statusBarController?.let { controller ->
                if (brightness != null) {
                    controller.isLight = brightness is Brightness.Light
                }
            }
        }
    }

    private val _navigationBarBrightnessStack = BrightnessStack().apply {
        registerCallback { brightness ->
            _navigationBarController?.let { controller ->
                if (brightness != null) {
                    controller.isLight = brightness is Brightness.Light
                }
            }
        }
    }

    val statusBarBrightnessStack: IBrightnessStack
        get() = _statusBarBrightnessStack

    val navigationBarBrightnessStack: IBrightnessStack
        get() = _navigationBarBrightnessStack

    fun registerStatusBarController(controller: IStatusBarController) {
        _statusBarController = controller
    }

    fun unregisterStatusBarController(controller: IStatusBarController) {
        if (_statusBarController == controller) {
            _statusBarController = null
        }
    }

    fun registerNavigationBarController(controller: INavigationBarController) {
        _navigationBarController = controller
    }

    fun unregisterNavigationBarController(controller: INavigationBarController) {
        if (_navigationBarController == controller) {
            _navigationBarController = null
        }
    }
}