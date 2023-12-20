package com.sd.demo.compose_systemui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sd.demo.compose_systemui.databinding.SampleViewBinding
import com.sd.lib.compose.systemui.IStatusBarController

class SampleView : ComponentActivity() {
    private val _binding by lazy { SampleViewBinding.inflate(layoutInflater) }

    private val _statusBarController by lazy {
        IStatusBarController.create(_binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)

        _binding.btnLight.setOnClickListener {
            _statusBarController.isLight = true
        }

        _binding.btnDark.setOnClickListener {
            _statusBarController.isLight = false
        }

        _binding.btnShow.setOnClickListener {
            _statusBarController.isVisible = true
        }

        _binding.btnHide.setOnClickListener {
            _statusBarController.isVisible = false
        }
    }
}