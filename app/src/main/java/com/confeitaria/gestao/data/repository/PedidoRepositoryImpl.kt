package com.confeitaria.gestao.data.repository

import com.confeitaria.gestao.data.local.database.dao.PedidoDao
import com.confeitaria.gestao.data.local.database.entity.PedidoEntity
import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.domain.model.enums.StatusPedido
import com.confeitaria.gestao.domain.model.enums.TipoEntrega
import com.confeitaria.gestao.domain.repository.PedidoRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PedidoRepositoryImpl @Inject constructor(
    private val pedidoDao: PedidoDao
) : PedidoRepository {

    override fun getAll(): Flow<List<Pedido>> = pedidoDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    override fun getByStatus(status: String): Flow<List<Pedido>> = pedidoDao.getByStatus(status).map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getById(id: Long): Pedido? = pedidoDao.getById(id)?.toDomain()

    override suspend fun save(pedido: Pedido): Long = pedidoDao.insert(pedido.toEntity())

    override suspend fun updateStatus(id: Long, status: String) = pedidoDao.updateStatus(id, status)

    override fun countAtivos(): Flow<Int> = pedidoDao.countAtivos()

    override fun getReceitaPeriodo(inicio: Long, fim: Long): Flow<Double> = 
        pedidoDao.getReceitaPeriodo(inicio, fim).map { it ?: 0.0 }

    private fun PedidoEntity.toDomain() = Pedido(
        id = id, clienteId = clienteId, dataPedido = dataPedido,
        dataEntrega = dataEntrega, horaEntrega = horaEntrega,
        tipoEntrega = TipoEntrega.valueOf(tipoEntrega),
        enderecoId = enderecoId, status = StatusPedido.valueOf(status),
        totalProdutos = totalProdutos, totalFrete = totalFrete,
        desconto = desconto, totalFinal = totalFinal, observacoes = observacoes
    )

    private fun Pedido.toEntity() = PedidoEntity(
        id = id, clienteId = clienteId, dataPedido = dataPedido,
        dataEntrega = dataEntrega, horaEntrega = horaEntrega,
        tipoEntrega = tipoEntrega.name,
        enderecoId = enderecoId, status = status.name,
        totalProdutos = totalProdutos, totalFrete = totalFrete,
        desconto = desconto, totalFinal = totalFinal, observacoes = observacoes
    )
}
