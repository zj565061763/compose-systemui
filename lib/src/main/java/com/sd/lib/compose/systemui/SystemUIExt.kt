package com.sd.lib.compose.systemui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.activity.viewModels

fun androidx.activity.ComponentActivity.fStatusBarBrightnessStack(): IBrightnessStack {
  val viewModel by viewModels<StatusBarViewModel>()
  if (viewModel.getController() == null) {
    val controller = IStatusBarController.create(view = window.decorView, window = window)
    viewModel.registerController(controller)
  }
  return viewModel.brightnessStack
}

fun androidx.activity.ComponentActivity.fNavigationBarBrightnessStack(): IBrightnessStack {
  val viewModel by viewModels<NavigationBarViewModel>()
  if (viewModel.getController() == null) {
    val controller = INavigationBarController.create(view = window.decorView, window = window)
    viewModel.registerController(controller)
  }
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