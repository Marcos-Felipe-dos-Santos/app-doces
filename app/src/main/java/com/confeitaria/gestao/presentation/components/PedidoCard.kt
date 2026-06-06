package com.confeitaria.gestao.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.presentation.util.toMoedaBR
import com.confeitaria.gestao.presentation.util.toDataHoraBR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoCard(pedido: Pedido, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = pedido.clienteNome, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                StatusChip(pedido.status)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Entrega: ${pedido.dataEntrega?.toDataHoraBR() ?: "N/A"}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = pedido.totalFinal.toMoedaBR(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}
