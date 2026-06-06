package com.confeitaria.gestao.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.confeitaria.gestao.domain.model.enums.StatusPedido
import com.confeitaria.gestao.presentation.theme.*

@Composable
fun StatusChip(status: StatusPedido) {
    val color = when (status) {
        StatusPedido.PENDENTE -> CorPendente
        StatusPedido.EM_PRODUCAO -> CorEmProducao
        StatusPedido.PRONTO -> CorPronto
        StatusPedido.SAIU_ENTREGA -> CorSaiuEntrega
        StatusPedido.ENTREGUE -> CorEntregue
        StatusPedido.CANCELADO -> CorCancelado
    }

    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = status.label, color = Color.White, fontSize = 12.sp)
    }
}
