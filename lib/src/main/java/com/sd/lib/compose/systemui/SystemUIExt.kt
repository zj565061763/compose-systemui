package com.sd.lib.compose.systemui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.activity.viewModels

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