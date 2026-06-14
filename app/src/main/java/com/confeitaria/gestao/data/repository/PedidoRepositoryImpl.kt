package com.confeitaria.gestao.data.repository

import com.confeitaria.gestao.data.local.database.dao.ClienteDao
import com.confeitaria.gestao.data.local.database.dao.ItemPedidoDao
import com.confeitaria.gestao.data.local.database.dao.PagamentoDao
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
    private val clienteDao: ClienteDao,
    private val pagamentoDao: PagamentoDao
) : PedidoRepository {

    override fun getAll(): Flow<List<Pedido>> = combine(
        pedidoDao.getAll(),
        clienteDao.getAll()
    ) { pedidos, clientes ->
        val clienteMap = clientes.associateBy { it.id }
        pedidos.map { it.toDomain(clienteNome = clienteMap[it.clienteId]?.nome ?: "", clienteTelefone = clienteMap[it.clienteId]?.telefone ?: "") }
    }

    override fun getByStatus(status: String): Flow<List<Pedido>> = combine(
        pedidoDao.getByStatus(status),
        clienteDao.getAll()
    ) { pedidos, clientes ->
        val clienteMap = clientes.associateBy { it.id }
        pedidos.map { it.toDomain(clienteNome = clienteMap[it.clienteId]?.nome ?: "", clienteTelefone = clienteMap[it.clienteId]?.telefone ?: "") }
    }

    override suspend fun getById(id: Long): Pedido? {
        val entity = pedidoDao.getById(id) ?: return null
        val cliente = clienteDao.getById(entity.clienteId)
        val itens = itemPedidoDao.getByPedido(id).first().map { it.toDomain() }
        return entity.toDomain(clienteNome = cliente?.nome ?: "", clienteTelefone = cliente?.telefone ?: "", itens = itens)
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

    override fun getReceitaPeriodo(inicio: Long, fim: Long): Flow<Long> =
        pedidoDao.getReceitaPeriodo(inicio, fim).map { it ?: 0L }

    override fun getPedidosPeriodo(inicio: Long, fim: Long): Flow<List<Pedido>> = combine(
        pedidoDao.getPedidosPeriodo(inicio, fim),
        clienteDao.getAll()
    ) { pedidos, clientes ->
        val clienteMap = clientes.associateBy { it.id }
        pedidos.map { it.toDomain(clienteNome = clienteMap[it.clienteId]?.nome ?: "", clienteTelefone = clienteMap[it.clienteId]?.telefone ?: "") }
    }

    override fun getTotalRecebidoPeriodo(inicio: Long, fim: Long): Flow<Long> =
        pagamentoDao.getTotalRecebidoPeriodo(inicio, fim)

    private fun PedidoEntity.toDomain(clienteNome: String = "", clienteTelefone: String = "", itens: List<ItemPedido> = emptyList()) = Pedido(
        id = id, clienteId = clienteId, clienteNome = clienteNome, clienteTelefone = clienteTelefone,
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
