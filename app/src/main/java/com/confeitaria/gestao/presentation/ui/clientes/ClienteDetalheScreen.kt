package com.confeitaria.gestao.presentation.ui.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import com.confeitaria.gestao.presentation.navigation.Screen
import com.confeitaria.gestao.presentation.util.abrirMaps
import com.confeitaria.gestao.presentation.util.abrirWhatsApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteDetalheScreen(
    navController: NavController,
    viewModel: ClienteDetalheViewModel = hiltViewModel()
) {
    val cliente by viewModel.cliente.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cliente?.nome ?: "Detalhes do Cliente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    cliente?.let { c ->
                        IconButton(onClick = {
                            navController.navigate(Screen.ClienteForm.createRoute(c.id))
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (cliente == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val c = cliente!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Column {
                                Text(text = "Nome", style = MaterialTheme.typography.labelSmall)
                                Text(text = c.nome, fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Column {
                                Text(text = "Telefone", style = MaterialTheme.typography.labelSmall)
                                Text(text = c.telefone, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (!c.email.isNullOrBlank()) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Icon(Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Column {
                                    Text(text = "E-mail", style = MaterialTheme.typography.labelSmall)
                                    Text(text = c.email, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        if (!c.observacoes.isNullOrBlank()) {
                            HorizontalDivider()
                            Text(
                                text = c.observacoes,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        HorizontalDivider()
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { abrirWhatsApp(context, c.telefone, "Olá ${c.nome}!") },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("WhatsApp")
                            }
                            val enderecoMaps = c.enderecos.firstOrNull { it.principal }
                                ?: c.enderecos.firstOrNull()
                            if (enderecoMaps != null) {
                                OutlinedButton(
                                    onClick = { abrirMaps(context, enderecoMaps.enderecoCompleto) },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                    Text("Ver no Mapa")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
