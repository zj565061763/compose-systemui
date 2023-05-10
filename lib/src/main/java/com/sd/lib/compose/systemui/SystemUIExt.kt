package com.sd.lib.compose.systemui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.activity.viewModels

fun androidx.activity.ComponentActivity.statusBarBrightnessStack(): IBrightnessStack {
    val viewModel by viewModels<BrightnessStackViewModel>()
    return viewModel.statusBarBrightnessStack
}

fun androidx.activity.ComponentActivity.navigationBarBrightnessStack(): IBrightnessStack {
    val viewModel by viewModels<BrightnessStackViewModel>()
    return viewModel.navigationBarBrightnessStack
}

fun View.statusBarBrightnessStack(): IBrightnessStack? {
    val activity = context.findActivity()
    return if (activity is androidx.activity.ComponentActivity) {
        activity.statusBarBrightnessStack()
    } else {
        null
    }
}

fun View.navigationBarBrightnessStack(): IBrightnessStack? {
    val activity = context.findActivity()
    return if (activity is androidx.activity.ComponentActivity) {
        activity.navigationBarBrightnessStack()
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