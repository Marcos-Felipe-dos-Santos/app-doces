package com.confeitaria.gestao.domain.usecase.pedido

import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.domain.repository.PedidoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPedidosUseCase @Inject constructor(private val repository: PedidoRepository) {
    operator fun invoke(): Flow<List<Pedido>> = repository.getAll()
}

class SavePedidoUseCase @Inject constructor(private val repository: PedidoRepository) {
    suspend operator fun invoke(pedido: Pedido): Long = repository.save(pedido)
}

class UpdateStatusPedidoUseCase @Inject constructor(private val repository: PedidoRepository) {
    suspend operator fun invoke(id: Long, status: String) = repository.updateStatus(id, status)
}

class GetPedidoByIdUseCase @Inject constructor(private val repository: PedidoRepository) {
    suspend operator fun invoke(id: Long): Pedido? = repository.getById(id)
}
