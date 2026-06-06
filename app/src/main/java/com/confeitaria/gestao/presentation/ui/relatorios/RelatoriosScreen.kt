package com.confeitaria.gestao.presentation.ui.relatorios

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.confeitaria.gestao.presentation.components.ConfeitariaTopBar
import com.confeitaria.gestao.presentation.components.EmptyState

@Composable
fun RelatoriosScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { ConfeitariaTopBar(title = "Relatórios") }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            EmptyState(
                message = "Relatórios serão implementados em breve",
                icon = Icons.Default.Assessment
            )
        }
    }
}
