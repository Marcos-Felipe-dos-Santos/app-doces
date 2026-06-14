package com.confeitaria.gestao

import com.confeitaria.gestao.domain.model.ItemPedido
import com.confeitaria.gestao.domain.usecase.pedido.CalcularPedido
import org.junit.Assert.assertEquals
import org.junit.Test

class CalcularPedidoTest {

    @Test
    fun testSubtotalItem() {
        assertEquals(15000L, CalcularPedido.subtotalItem(3, 5000L))
    }

    @Test
    fun testTotalProdutos() {
        val itens = listOf(
            ItemPedido(pedidoId = 1, produtoId = 1, quantidade = 2, precoUnitario = 3000L),
            ItemPedido(pedidoId = 1, produtoId = 2, quantidade = 1, precoUnitario = 5000L)
        )
        assertEquals(11000L, CalcularPedido.totalProdutos(itens))
    }

    @Test
    fun testTotalFinal() {
        assertEquals(12500L, CalcularPedido.totalFinal(10000L, 3000L, 500L))
    }

    @Test
    fun testTotalComJuros_10percent() {
        assertEquals(11000L, CalcularPedido.totalComJuros(10000L, 10))
    }

    @Test
    fun testTotalComJuros_0percent() {
        assertEquals(10000L, CalcularPedido.totalComJuros(10000L, 0))
    }

    @Test
    fun testValorParcela_3x() {
        assertEquals(10000L, CalcularPedido.valorParcela(30000L, 3))
    }

    @Test
    fun testValorParcela_invalido() {
        assertEquals(30000L, CalcularPedido.valorParcela(30000L, 0))
        assertEquals(30000L, CalcularPedido.valorParcela(30000L, -1))
    }

    @Test
    fun testValorPendente() {
        assertEquals(7000L, CalcularPedido.valorPendente(10000L, 3000L))
    }

    @Test
    fun testFreteCalculado_acimaDaMinima() {
        // 10km * 200 centavos/km = 2000, taxa mínima = 500 → usa calculado
        assertEquals(2000L, CalcularPedido.freteCalculado(10.0, 200L, 500L))
    }

    @Test
    fun testFreteCalculado_abaixoDaMinima() {
        // 1km * 200 centavos/km = 200, taxa mínima = 500 → usa mínima
        assertEquals(500L, CalcularPedido.freteCalculado(1.0, 200L, 500L))
    }

    @Test
    fun testFreteCalculado_exato() {
        // 5km * 100 centavos/km = 500, taxa mínima = 500 → empate, retorna 500
        assertEquals(500L, CalcularPedido.freteCalculado(5.0, 100L, 500L))
    }
}
