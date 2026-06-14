package com.confeitaria.gestao.presentation.ui.pedidos

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.confeitaria.gestao.presentation.components.StatusChip
import com.confeitaria.gestao.presentation.util.abrirWhatsApp
import com.confeitaria.gestao.presentation.util.toDataBR
import com.confeitaria.gestao.presentation.util.toMoedaBR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoDetalheScreen(
    navController: NavController,
    viewModel: PedidoDetalheViewModel = hiltViewModel()
) {
    val pedido by viewModel.pedido.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Pedido") },
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
        if (pedido == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val p = pedido!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Pedido #${p.id}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    StatusChip(p.status)
                }

                // Cliente
                Text("Cliente", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(p.clienteNome, fontWeight = FontWeight.Bold)
                        Text(
                            text = "${p.tipoEntrega.label}${p.dataEntrega?.let { " · ${it.toDataBR()}" } ?: ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Itens
                Text("Itens", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (p.itens.isEmpty()) {
                            Text(
                                text = "Nenhum item registrado",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            p.itens.forEach { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${item.quantidade}x ${item.produtoNome}", modifier = Modifier.weight(1f))
                                    Text(item.subtotal.toMoedaBR(), fontWeight = FontWeight.Medium)
                                }
                            }
                            HorizontalDivider()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total", fontWeight = FontWeight.Bold)
                                Text(p.totalFinal.toMoedaBR(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }

                if (!p.observacoes.isNullOrBlank()) {
                    Text("Observações", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = p.observacoes,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                if (p.clienteTelefone.isNotBlank()) {
                    val msgEntrega = p.dataEntrega?.let { " para ${it.toDataBR()}" } ?: ""
                    Button(
                        onClick = {
                            abrirWhatsApp(
                                context,
                                p.clienteTelefone,
                                "Olá ${p.clienteNome}! Seu pedido #${p.id} está confirmado$msgEntrega."
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Avisar cliente no WhatsApp")
                    }
                }
            }
        }
    }
}
