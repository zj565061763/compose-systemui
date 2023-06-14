package com.sd.lib.compose.systemui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView

fun androidx.activity.ComponentActivity.fStatusBarBrightnessStack(): IBrightnessStack {
    val viewModel by viewModels<BrightnessStackViewModel>()
    return viewModel.statusBarBrightnessStack
}

fun androidx.activity.ComponentActivity.fNavigationBarBrightnessStack(): IBrightnessStack {
    val viewModel by viewModels<BrightnessStackViewModel>()
    return viewModel.navigationBarBrightnessStack
}

fun View.fStatusBarBrightnessStack(): IBrightnessStack? {
    val activity = context.findActivity()
    return if (activity is androidx.activity.ComponentActivity) {
        activity.fStatusBarBrightnessStack()
    } else {
        null
    }
}

fun View.fNavigationBarBrightnessStack(): IBrightnessStack? {
    val activity = context.findActivity()
    return if (activity is androidx.activity.ComponentActivity) {
        activity.fNavigationBarBrightnessStack()
    } else {
        null
    }
}

private tailrec fun Context.findActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

class FSystemUIView(
    context: Context,
    attrs: AttributeSet?,
) : FrameLayout(context, attrs) {

    private val _composeView = ComposeView(context)
    private var _statusBarController: IStatusBarController? = null
    private var _navigationBarController: INavigationBarController? = null

    val statusBarController: IStatusBarController
        get() = checkNotNull(_statusBarController)

    val navigationBarController: INavigationBarController
        get() = checkNotNull(_navigationBarController)

    init {
        addView(_composeView)
        _composeView.setContent {
            FSystemUI {
                _statusBarController = fStatusBarController()
                _navigationBarController = fNavigationBarController()
            }
        }
    }
}