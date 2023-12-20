package com.sd.demo.compose_systemui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sd.lib.compose.systemui.ISystemUIController

/**
 * 颜色
 */
@Composable
fun BarColor(controller: ISystemUIController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Button(
            onClick = {
                controller.color = Color.Blue
            }
        ) {
            Text("Blue")
        }

        Button(
            onClick = {
                controller.color = Color.Transparent
            }
        ) {
            Text("Transparent")
        }

    }
}

@Composable
fun BarVisibility(controller: ISystemUIController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Button(
            onClick = {
                controller.isVisible = true
            }
        ) {
            Text("show")
        }

        Button(
            onClick = {
                controller.isVisible = false
            }
        ) {
            Text("hide")
        }
    }
}