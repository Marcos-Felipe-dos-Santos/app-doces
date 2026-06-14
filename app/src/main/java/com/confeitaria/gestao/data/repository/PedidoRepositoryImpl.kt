package com.confeitaria.gestao.data.repository

import com.confeitaria.gestao.data.local.database.dao.ClienteDao
import com.confeitaria.gestao.data.local.database.dao.ItemPedidoDao
import com.confeitaria.gestao.data.local.database.dao.PedidoDao
import com.confeitaria.gestao.data.local.database.entity.ItemPedidoEntity
import com.confeitaria.gestao.data.local.database.entity.PedidoEntity
import com.confeitaria.gestao.domain.model.ItemPedido
import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.domain.model.enums.StatusPedido
import com.confeitaria.gestao.domain.model.enums.TipoEntrega
import com.confeitaria.gestao.domain.repository.PedidoRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PedidoRepositoryImpl @Inject constructor(
    private val pedidoDao: PedidoDao,
    private val itemPedidoDao: ItemPedidoDao,
    private val clienteDao: ClienteDao
) : PedidoRepository {

    override fun getAll(): Flow<List<Pedido>> = combine(
        pedidoDao.getAll(),
        clienteDao.getAll()
    ) { pedidos, clientes ->
        val clienteMap = clientes.associateBy { it.id }
        pedidos.map { it.toDomain(clienteNome = clienteMap[it.clienteId]?.nome ?: "") }
    }

    override fun getByStatus(status: String): Flow<List<Pedido>> = combine(
        pedidoDao.getByStatus(status),
        clienteDao.getAll()
    ) { pedidos, clientes ->
        val clienteMap = clientes.associateBy { it.id }
        pedidos.map { it.toDomain(clienteNome = clienteMap[it.clienteId]?.nome ?: "") }
    }

    override suspend fun getById(id: Long): Pedido? {
        val entity = pedidoDao.getById(id) ?: return null
        val clienteNome = clienteDao.getById(entity.clienteId)?.nome ?: ""
        val itens = itemPedidoDao.getByPedido(id).first().map { it.toDomain() }
        return entity.toDomain(clienteNome = clienteNome, itens = itens)
    }

    override suspend fun save(pedido: Pedido): Long {
        val pedidoId = pedidoDao.insert(pedido.toEntity())
        if (pedido.itens.isNotEmpty()) {
            itemPedidoDao.insertAll(pedido.itens.map { it.toEntity(pedidoId) })
        }
        return pedidoId
    }

    override suspend fun updateStatus(id: Long, status: String) = pedidoDao.updateStatus(id, status)

    override fun countAtivos(): Flow<Int> = pedidoDao.countAtivos()

    override fun getReceitaPeriodo(inicio: Long, fim: Long): Flow<Double> =
        pedidoDao.getReceitaPeriodo(inicio, fim).map { it ?: 0.0 }

    private fun PedidoEntity.toDomain(clienteNome: String = "", itens: List<ItemPedido> = emptyList()) = Pedido(
        id = id, clienteId = clienteId, clienteNome = clienteNome,
        dataPedido = dataPedido, dataEntrega = dataEntrega, horaEntrega = horaEntrega,
        tipoEntrega = TipoEntrega.valueOf(tipoEntrega),
        enderecoId = enderecoId, status = StatusPedido.valueOf(status),
        totalProdutos = totalProdutos, totalFrete = totalFrete,
        desconto = desconto, totalFinal = totalFinal, observacoes = observacoes,
        itens = itens
    )

    private fun Pedido.toEntity() = PedidoEntity(
        id = id, clienteId = clienteId, dataPedido = dataPedido,
        dataEntrega = dataEntrega, horaEntrega = horaEntrega,
        tipoEntrega = tipoEntrega.name, enderecoId = enderecoId, status = status.name,
        totalProdutos = totalProdutos, totalFrete = totalFrete,
        desconto = desconto, totalFinal = totalFinal, observacoes = observacoes
    )

    private fun ItemPedidoEntity.toDomain() = ItemPedido(
        id = id, pedidoId = pedidoId, produtoId = produtoId,
        variacaoId = variacaoId, quantidade = quantidade,
        precoUnitario = precoUnitario, observacoes = observacoes
    )

    private fun ItemPedido.toEntity(pedidoId: Long) = ItemPedidoEntity(
        pedidoId = pedidoId, produtoId = produtoId,
        variacaoId = variacaoId, quantidade = quantidade,
        precoUnitario = precoUnitario, observacoes = observacoes
    )
}
