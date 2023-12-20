package com.sd.demo.compose_systemui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sd.demo.compose_systemui.databinding.ActivitySampleViewBinding
import com.sd.lib.compose.systemui.Brightness
import com.sd.lib.compose.systemui.IStatusBarController
import com.sd.lib.compose.systemui.fStatusBarBrightnessStack

class SampleView : ComponentActivity() {
    private val _binding by lazy { ActivitySampleViewBinding.inflate(layoutInflater) }

    private val _statusBarController by lazy {
        IStatusBarController.create(_binding.root)
    }

    private val _light = Brightness.light()
    private val _dark = Brightness.dark()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)

        _binding.btnLight.setOnClickListener {
            fStatusBarBrightnessStack().add(_light)
        }
        _binding.btnDark.setOnClickListener {
            fStatusBarBrightnessStack().add(_dark)
        }
        _binding.btnToggleVisible.setOnClickListener {
            _statusBarController.isVisible = !_statusBarController.isVisible
        }
    }
}