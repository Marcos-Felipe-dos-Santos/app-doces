package com.confeitaria.gestao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.confeitaria.gestao.presentation.navigation.AppNavGraph
import com.confeitaria.gestao.presentation.theme.ConfeitariaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConfeitariaTheme {
                AppNavGraph()
            }
        }
    }
}
