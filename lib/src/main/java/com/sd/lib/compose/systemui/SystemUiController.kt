/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sd.lib.compose.systemui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun rememberStatusBarController(
    window: Window? = findWindow(),
): IStatusBarController {
    val view = LocalView.current
    return remember(view, window) {
        IStatusBarController.create(view, window)
    }
}

@Composable
fun rememberNavigationBarController(
    window: Window? = findWindow(),
): INavigationBarController {
    val view = LocalView.current
    return remember(view, window) {
        INavigationBarController.create(view, window)
    }
}

interface ISystemUiController {
    var behavior: Int

    var isVisible: Boolean

    var isLight: Boolean

    var color: Color
}

interface IStatusBarController : ISystemUiController {
    companion object {
        fun create(
            view: View,
            window: Window? = view.context.findWindow(),
        ): IStatusBarController {
            return StatusBarController(view, window)
        }
    }
}

interface INavigationBarController : ISystemUiController {
    companion object {
        fun create(
            view: View,
            window: Window? = view.context.findWindow(),
        ): INavigationBarController {
            return NavigationBarController(view, window)
        }
    }
}

private abstract class BaseSystemUiController(
    val view: View,
    val window: Window?
) {
    val windowInsetsController = window?.let {
        WindowCompat.getInsetsController(it, view)
    }
}

private class StatusBarController(
    view: View,
    window: Window?
) : BaseSystemUiController(view, window), IStatusBarController {

    override var behavior: Int
        get() = windowInsetsController?.systemBarsBehavior ?: 0
        set(value) {
            windowInsetsController?.systemBarsBehavior = value
        }

    override var isVisible: Boolean
        get() {
            return ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.statusBars()) == true
        }
        set(value) {
            if (value) {
                windowInsetsController?.show(WindowInsetsCompat.Type.statusBars())
            } else {
                windowInsetsController?.hide(WindowInsetsCompat.Type.statusBars())
            }
        }

    override var isLight: Boolean
        get() = windowInsetsController?.isAppearanceLightStatusBars == true
        set(value) {
            windowInsetsController?.isAppearanceLightStatusBars = value
        }

    override var color: Color
        get() = Color(window?.statusBarColor ?: 0x00000000)
        set(value) {
            window?.statusBarColor = when {
                isLight && windowInsetsController?.isAppearanceLightStatusBars != true -> {
                    // If we're set to use dark icons, but our windowInsetsController call didn't
                    // succeed (usually due to API level), we instead transform the color to maintain
                    // contrast
                    transformColorForLightContent(value)
                }
                else -> value
            }.toArgb()
        }
}

private class NavigationBarController(
    view: View,
    window: Window?
) : BaseSystemUiController(view, window), INavigationBarController {

    override var behavior: Int
        get() = windowInsetsController?.systemBarsBehavior ?: 0
        set(value) {
            windowInsetsController?.systemBarsBehavior = value
        }

    override var isVisible: Boolean
        get() {
            return ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.navigationBars()) == true
        }
        set(value) {
            if (value) {
                windowInsetsController?.show(WindowInsetsCompat.Type.navigationBars())
            } else {
                windowInsetsController?.hide(WindowInsetsCompat.Type.navigationBars())
            }
        }

    override var isLight: Boolean
        get() = windowInsetsController?.isAppearanceLightNavigationBars == true
        set(value) {
            windowInsetsController?.isAppearanceLightNavigationBars = value
        }

    override var color: Color
        get() = Color(window?.navigationBarColor ?: 0x00000000)
        set(value) {
            window?.navigationBarColor = when {
                isLight && windowInsetsController?.isAppearanceLightNavigationBars != true -> {
                    // If we're set to use dark icons, but our windowInsetsController call didn't
                    // succeed (usually due to API level), we instead transform the color to maintain
                    // contrast
                    transformColorForLightContent(value)
                }
                else -> value
            }.toArgb()
        }
}

@Composable
private fun findWindow(): Window? =
    (LocalView.current.parent as? DialogWindowProvider)?.window
        ?: LocalView.current.context.findWindow()

private tailrec fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }

private fun transformColorForLightContent(color: Color): Color {
    val blackScrim = Color(0f, 0f, 0f, 0.3f) // 30% opaque black
    return blackScrim.compositeOver(color)
}