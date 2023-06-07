package com.mirvan.devtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mirvan.devtest.employee_feature.presentation.EmployeeScreen
import com.mirvan.devtest.ui.theme.DevtestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevtestTheme {
                EmployeeScreen()
            }
        }
    }
}
