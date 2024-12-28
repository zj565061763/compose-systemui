package com.sd.demo.compose_systemui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.sd.demo.compose_systemui.ui.theme.AppTheme
import com.sd.lib.compose.systemui.FStatusBarDark
import com.sd.lib.compose.systemui.FStatusBarLight
import com.sd.lib.compose.systemui.FSystemUI
import com.sd.lib.compose.systemui.fStatusBarController

class SampleStatusBar : ComponentActivity() {
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
    BarBrightness { isLight ->
      if (isLight) {
        FStatusBarLight()
      } else {
        FStatusBarDark()
      }
    }

    HorizontalDivider()
    BarColor(fStatusBarController())

    HorizontalDivider()
    BarVisibility(fStatusBarController())
  }
}