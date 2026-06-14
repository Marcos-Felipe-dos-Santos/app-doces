package com.confeitaria.gestao.domain.usecase.cliente

import com.confeitaria.gestao.domain.model.Cliente
import com.confeitaria.gestao.domain.repository.ClienteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientesUseCase @Inject constructor(private val repository: ClienteRepository) {
    operator fun invoke(): Flow<List<Cliente>> = repository.getAll()
}

class SaveClienteUseCase @Inject constructor(private val repository: ClienteRepository) {
    suspend operator fun invoke(cliente: Cliente): Long = repository.save(cliente)
}

class DeleteClienteUseCase @Inject constructor(private val repository: ClienteRepository) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}

class GetClienteByIdUseCase @Inject constructor(private val repository: ClienteRepository) {
    suspend operator fun invoke(id: Long): Cliente? = repository.getById(id)
}

class SearchClientesUseCase @Inject constructor(private val repository: ClienteRepository) {
    operator fun invoke(query: String): Flow<List<Cliente>> = repository.search(query)
}
