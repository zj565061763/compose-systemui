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
    val viewModel by viewModels<StatusBarViewModel>()
    return viewModel.brightnessStack
}

fun androidx.activity.ComponentActivity.fNavigationBarBrightnessStack(): IBrightnessStack {
    val viewModel by viewModels<NavigationBarViewModel>()
    return viewModel.brightnessStack
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
    init {
        val composeView = ComposeView(context).apply {
            setContent {
                FSystemUI {
                }
            }
        }
        addView(composeView)
    }
}