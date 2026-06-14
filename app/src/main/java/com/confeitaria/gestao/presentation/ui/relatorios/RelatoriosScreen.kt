package com.confeitaria.gestao.presentation.ui.relatorios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.confeitaria.gestao.presentation.components.ConfeitariaTopBar
import com.confeitaria.gestao.presentation.util.toMoedaBR
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RelatoriosScreen(
    navController: NavController,
    viewModel: RelatoriosViewModel = hiltViewModel()
) {
    val relatorio by viewModel.relatorio.collectAsState()
    val mesAtual = SimpleDateFormat("MMMM yyyy", Locale("pt", "BR")).format(Date())

    Scaffold(
        topBar = { ConfeitariaTopBar(title = "Relatórios") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Resumo de ${mesAtual.replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            RelatorioCard(label = "Total de pedidos", valor = "${relatorio.totalPedidos} pedidos")
            RelatorioCard(label = "Faturamento bruto", valor = relatorio.faturamentoBruto.toMoedaBR())
            RelatorioCard(label = "Recebido", valor = relatorio.recebido.toMoedaBR(), destaque = true)
            RelatorioCard(label = "Pendente", valor = relatorio.pendente.toMoedaBR())
            RelatorioCard(label = "Ticket médio", valor = relatorio.ticketMedio.toMoedaBR())
        }
    }
}

@Composable
private fun RelatorioCard(label: String, valor: String, destaque: Boolean = false) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = valor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (destaque) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
