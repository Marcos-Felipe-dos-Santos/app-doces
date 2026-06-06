package com.confeitaria.gestao.presentation.ui.configuracoes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.confeitaria.gestao.presentation.components.ConfeitariaTopBar

@Composable
fun ConfiguracoesScreen(
    navController: NavController,
    viewModel: ConfiguracoesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var nome by remember { mutableStateOf("") }
    var pix by remember { mutableStateOf("") }
    var precoPorKm by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        nome = uiState.nomeConfeiteira
        pix = uiState.pixKey
        precoPorKm = uiState.precoPorKm.toString()
        endereco = uiState.enderecoBase
    }

    Scaffold(
        topBar = { ConfeitariaTopBar(title = "Configurações") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome da Confeiteira") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = pix,
                onValueChange = { pix = it },
                label = { Text("Chave PIX") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = precoPorKm,
                onValueChange = { precoPorKm = it },
                label = { Text("Preço por Km (R$)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço Base") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val preco = precoPorKm.toDoubleOrNull() ?: 0.0
                    viewModel.saveConfig(nome, pix, preco, endereco)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Configurações")
            }
        }
    }
}
