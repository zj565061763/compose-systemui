package com.sd.demo.compose_systemui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.sd.demo.compose_systemui.ui.theme.AppTheme
import com.sd.lib.compose.systemui.FNavigationBarDark
import com.sd.lib.compose.systemui.FNavigationBarLight
import com.sd.lib.compose.systemui.FSystemUI
import com.sd.lib.compose.systemui.fNavigationBarController

class SampleNavigationBar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                FSystemUI {
                    Content()
                }
            }
        }
    }
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        BarBrightness()

        Divider()
        BarColor(fNavigationBarController())

        Divider()
        BarVisibility(fNavigationBarController())
    }
}

/**
 * 明亮度
 */
@Composable
private fun BarBrightness() {
    var isLight by remember { mutableStateOf(false) }

    if (isLight) {
        FNavigationBarLight()
    } else {
        FNavigationBarDark()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Button(
            onClick = {
                isLight = true
            }
        ) {
            Text("light")
        }

        Button(
            onClick = {
                isLight = false
            }
        ) {
            Text("dark")
        }
    }
}