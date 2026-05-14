package com.example.techscreentemplate

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.techscreentemplate.ui.theme.TechScreenTemplateTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechScreenTemplateTheme {
                ToastTechScreen()
            }
        }
    }
}

@HiltAndroidApp
class MyApplication : Application()
