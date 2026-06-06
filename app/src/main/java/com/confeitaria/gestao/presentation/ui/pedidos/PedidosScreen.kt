package com.confeitaria.gestao.presentation.ui.pedidos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.confeitaria.gestao.presentation.components.ConfeitariaTopBar
import com.confeitaria.gestao.presentation.components.EmptyState
import com.confeitaria.gestao.presentation.components.PedidoCard
import com.confeitaria.gestao.presentation.navigation.Screen

@Composable
fun PedidosScreen(
    navController: NavController,
    viewModel: PedidosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { ConfeitariaTopBar(title = "Pedidos") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.NovoPedido.createRoute()) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Novo Pedido")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                )
            } else if (uiState.pedidos.isEmpty()) {
                EmptyState(message = "Nenhum pedido cadastrado")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(uiState.pedidos, key = { it.id }) { pedido ->
                        PedidoCard(
                            pedido = pedido,
                            onClick = {
                                navController.navigate(Screen.PedidoDetalhe.createRoute(pedido.id))
                            }
                        )
                    }
                }
            }
        }
    }
}
