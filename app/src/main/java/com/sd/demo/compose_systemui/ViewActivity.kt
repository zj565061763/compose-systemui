package com.sd.demo.compose_systemui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sd.demo.compose_systemui.databinding.ActivityViewBinding
import com.sd.lib.compose.systemui.Brightness
import com.sd.lib.compose.systemui.fStatusBarBrightnessStack

class ViewActivity : ComponentActivity() {
    private val _binding by lazy { ActivityViewBinding.inflate(layoutInflater) }

    private val light = Brightness.light()
    private val dark = Brightness.dark()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)

        _binding.btnLight.setOnClickListener {
            fStatusBarBrightnessStack().add(light)
        }
        _binding.btnDark.setOnClickListener {
            fStatusBarBrightnessStack().add(dark)
        }
    }
}