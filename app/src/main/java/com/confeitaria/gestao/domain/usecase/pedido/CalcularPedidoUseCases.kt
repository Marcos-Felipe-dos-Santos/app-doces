package com.confeitaria.gestao.domain.usecase.pedido

import com.confeitaria.gestao.domain.model.ItemPedido

object CalcularPedido {

    fun subtotalItem(quantidade: Int, precoUnitarioCentavos: Long): Long =
        quantidade.toLong() * precoUnitarioCentavos

    fun totalProdutos(itens: List<ItemPedido>): Long =
        itens.sumOf { subtotalItem(it.quantidade, it.precoUnitario) }

    fun totalFinal(totalProdutos: Long, freteCentavos: Long, descontoCentavos: Long): Long =
        totalProdutos + freteCentavos - descontoCentavos

    fun totalComJuros(totalCentavos: Long, jurosPercent: Int): Long =
        totalCentavos * (100L + jurosPercent) / 100L

    fun valorParcela(totalComJurosCentavos: Long, numeroParcelas: Int): Long =
        if (numeroParcelas <= 0) totalComJurosCentavos
        else totalComJurosCentavos / numeroParcelas

    fun valorPendente(totalCentavos: Long, pagoCentavos: Long): Long =
        totalCentavos - pagoCentavos

    fun freteCalculado(distanciaKm: Double, precoPorKmCentavos: Long, taxaMinimaCentavos: Long): Long {
        val calculado = (distanciaKm * precoPorKmCentavos).toLong()
        return maxOf(calculado, taxaMinimaCentavos)
    }
}
