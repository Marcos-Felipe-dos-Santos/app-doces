package com.confeitaria.gestao.presentation.ui.catalogo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.confeitaria.gestao.presentation.util.toCentavos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProdutoFormScreen(
    navController: NavController,
    viewModel: ProdutoFormViewModel = hiltViewModel()
) {
    val salvo by viewModel.salvo.collectAsState()
    val categorias by viewModel.categorias.collectAsState()

    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var precoBase by remember { mutableStateOf("") }
    var categoriaSelecionadaId by remember { mutableStateOf<Long?>(null) }
    var expandedCategoria by remember { mutableStateOf(false) }

    LaunchedEffect(salvo) {
        if (salvo) navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Produto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            OutlinedTextField(
                value = precoBase,
                onValueChange = { precoBase = it },
                label = { Text("Preço Base *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                prefix = { Text("R$ ") }
            )

            if (categorias.isNotEmpty()) {
                val categoriaNome = categorias.find { it.id == categoriaSelecionadaId }?.nome ?: "Sem categoria"
                ExposedDropdownMenuBox(
                    expanded = expandedCategoria,
                    onExpandedChange = { expandedCategoria = it }
                ) {
                    OutlinedTextField(
                        value = categoriaNome,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoria") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoria) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCategoria,
                        onDismissRequest = { expandedCategoria = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sem categoria") },
                            onClick = { categoriaSelecionadaId = null; expandedCategoria = false }
                        )
                        categorias.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat.nome) },
                                onClick = { categoriaSelecionadaId = cat.id; expandedCategoria = false }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.salvar(nome, descricao, precoBase.toCentavos(), categoriaSelecionadaId)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nome.isNotBlank() && precoBase.isNotBlank()
            ) {
                Text("Salvar")
            }
        }
    }
}
