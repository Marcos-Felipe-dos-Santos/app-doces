package com.confeitaria.gestao.presentation.ui.novopedido

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.confeitaria.gestao.domain.model.Produto
import com.confeitaria.gestao.domain.model.enums.TipoEntrega
import com.confeitaria.gestao.presentation.util.toMoedaBR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoPedidoScreen(
    navController: NavController,
    viewModel: NovoPedidoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var expandedCliente by remember { mutableStateOf(false) }
    var showAddItemDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.pedidoSalvoId) {
        if (uiState.pedidoSalvoId != null) navController.popBackStack()
    }

    if (showAddItemDialog) {
        AdicionarItemDialog(
            produtos = uiState.produtos,
            onConfirm = { produto, quantidade ->
                viewModel.adicionarItem(produto, quantidade)
                showAddItemDialog = false
            },
            onDismiss = { showAddItemDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Pedido") },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Seção 1: Cliente
            Text("1. Cliente *", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            ExposedDropdownMenuBox(
                expanded = expandedCliente,
                onExpandedChange = { expandedCliente = it }
            ) {
                OutlinedTextField(
                    value = uiState.clienteSelecionado?.nome ?: "Selecione um cliente",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCliente) },
                    isError = uiState.clienteSelecionado == null,
                    supportingText = if (uiState.clientes.isEmpty()) {
                        { Text("Nenhum cliente cadastrado") }
                    } else null
                )
                ExposedDropdownMenu(
                    expanded = expandedCliente,
                    onDismissRequest = { expandedCliente = false }
                ) {
                    uiState.clientes.forEach { cliente ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(cliente.nome)
                                    Text(
                                        text = cliente.telefone,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            onClick = {
                                viewModel.selecionarCliente(cliente)
                                expandedCliente = false
                            }
                        )
                    }
                }
            }

            HorizontalDivider()

            // Seção 2: Tipo de entrega
            Text("2. Tipo de Entrega", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TipoEntrega.entries.forEach { tipo ->
                    FilterChip(
                        selected = uiState.tipoEntrega == tipo,
                        onClick = { viewModel.setTipoEntrega(tipo) },
                        label = { Text(tipo.label) }
                    )
                }
            }

            HorizontalDivider()

            // Seção 3: Itens
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("3. Itens *", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                TextButton(onClick = { showAddItemDialog = true }) {
                    Text("+ Adicionar")
                }
            }

            if (uiState.itens.isEmpty()) {
                Text(
                    text = "Nenhum item adicionado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                uiState.itens.forEachIndexed { index, item ->
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = item.produtoNome, fontWeight = FontWeight.Medium)
                                Text(
                                    text = "${item.quantidade}x ${item.precoUnitario.toMoedaBR()} = ${item.subtotal.toMoedaBR()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(onClick = { viewModel.removerItem(index) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Remover", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }

                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total", fontWeight = FontWeight.Bold)
                    Text(uiState.totalProdutos.toMoedaBR(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.salvar() },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.podesSalvar
            ) {
                if (uiState.salvando) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Salvar Pedido")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdicionarItemDialog(
    produtos: List<Produto>,
    onConfirm: (Produto, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var produtoSelecionado by remember { mutableStateOf<Produto?>(null) }
    var quantidade by remember { mutableStateOf("1") }
    var expandedProduto by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Item") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expandedProduto,
                    onExpandedChange = { expandedProduto = it }
                ) {
                    OutlinedTextField(
                        value = produtoSelecionado?.nome ?: "Selecione um produto",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Produto") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProduto) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedProduto,
                        onDismissRequest = { expandedProduto = false }
                    ) {
                        if (produtos.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("Nenhum produto cadastrado") },
                                onClick = { expandedProduto = false },
                                enabled = false
                            )
                        }
                        produtos.forEach { p ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(p.nome)
                                        Text(
                                            text = p.precoBase.toMoedaBR(),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                onClick = { produtoSelecionado = p; expandedProduto = false }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = quantidade,
                    onValueChange = { if (it.all { c -> c.isDigit() }) quantidade = it },
                    label = { Text("Quantidade") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val p = produtoSelecionado ?: return@Button
                    val q = quantidade.toIntOrNull()?.takeIf { it > 0 } ?: return@Button
                    onConfirm(p, q)
                },
                enabled = produtoSelecionado != null && (quantidade.toIntOrNull() ?: 0) > 0
            ) { Text("Adicionar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
