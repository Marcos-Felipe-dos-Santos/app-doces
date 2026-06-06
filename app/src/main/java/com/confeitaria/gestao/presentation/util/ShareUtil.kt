package com.confeitaria.gestao.presentation.util

import android.content.Context
import android.content.Intent
import com.confeitaria.gestao.domain.model.Pedido

fun sharePedido(context: Context, pedido: Pedido) {
    val text = """
        *Resumo do Pedido #${pedido.id}*
        Cliente: ${pedido.clienteNome}
        Entrega: ${pedido.dataEntrega?.toDataHoraBR()}
        Total: ${pedido.totalFinal.toMoedaBR()}
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Compartilhar Pedido"))
}
