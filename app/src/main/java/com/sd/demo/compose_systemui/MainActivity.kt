package com.sd.demo.compose_systemui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sd.demo.compose_systemui.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Content(
                    onClickStatusBar = {
                        startActivity(Intent(this, SampleStatusBar::class.java))
                    },
                    onClickNavigationBar = {
                        startActivity(Intent(this, SampleNavigation::class.java))
                    },
                    onClickView = {
                        startActivity(Intent(this, SampleView::class.java))
                    },
                )
            }
        }
    }
}

@Composable
private fun Content(
    onClickStatusBar: () -> Unit,
    onClickNavigationBar: () -> Unit,
    onClickView: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Button(
            onClick = onClickStatusBar
        ) {
            Text("StatusBar")
        }

        Button(
            onClick = onClickNavigationBar
        ) {
            Text("NavigationBar")
        }

        Button(
            onClick = onClickView
        ) {
            Text("View")
        }
    }
}