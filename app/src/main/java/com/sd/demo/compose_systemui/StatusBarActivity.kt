package com.sd.demo.compose_systemui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.sd.demo.compose_systemui.ui.theme.AppTheme
import com.sd.lib.compose.systemui.FStatusBarDark
import com.sd.lib.compose.systemui.FStatusBarLight
import com.sd.lib.compose.systemui.rememberStatusBarController

class StatusBarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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
        BarColor(rememberStatusBarController())

        Divider()
        BarVisibility(rememberStatusBarController())
    }
}

/**
 * 明亮度
 */
@Composable
private fun BarBrightness() {
    var isLight by remember { mutableStateOf(false) }

    if (isLight) {
        FStatusBarLight()
    } else {
        FStatusBarDark()
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