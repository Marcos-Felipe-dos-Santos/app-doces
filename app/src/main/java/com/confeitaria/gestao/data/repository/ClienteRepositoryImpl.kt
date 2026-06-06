package com.confeitaria.gestao.data.repository

import com.confeitaria.gestao.data.local.database.dao.ClienteDao
import com.confeitaria.gestao.data.local.database.dao.EnderecoDao
import com.confeitaria.gestao.data.local.database.entity.ClienteEntity
import com.confeitaria.gestao.data.local.database.entity.EnderecoEntity
import com.confeitaria.gestao.domain.model.Cliente
import com.confeitaria.gestao.domain.model.Endereco
import com.confeitaria.gestao.domain.repository.ClienteRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ClienteRepositoryImpl @Inject constructor(
    private val clienteDao: ClienteDao,
    private val enderecoDao: EnderecoDao
) : ClienteRepository {

    override fun getAll(): Flow<List<Cliente>> = clienteDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    override fun search(query: String): Flow<List<Cliente>> = clienteDao.search(query).map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getById(id: Long): Cliente? {
        val entity = clienteDao.getById(id) ?: return null
        val enderecos = enderecoDao.getByCliente(id).first().map { it.toDomain() }
        return entity.toDomain().copy(enderecos = enderecos)
    }

    override suspend fun save(cliente: Cliente): Long {
        return clienteDao.insert(cliente.toEntity())
    }

    override suspend fun delete(id: Long) {
        clienteDao.delete(id)
    }

    override suspend fun saveEndereco(endereco: Endereco): Long {
        if (endereco.principal) {
            enderecoDao.clearPrincipal(endereco.clienteId)
        }
        return enderecoDao.insert(endereco.toEntity())
    }

    override suspend fun deleteEndereco(endereco: Endereco) {
        enderecoDao.delete(endereco.toEntity())
    }

    private fun ClienteEntity.toDomain() = Cliente(
        id = id, nome = nome, telefone = telefone, email = email,
        observacoes = observacoes, dataCadastro = dataCadastro
    )

    private fun Cliente.toEntity() = ClienteEntity(
        id = id, nome = nome, telefone = telefone, email = email,
        observacoes = observacoes, dataCadastro = dataCadastro
    )

    private fun EnderecoEntity.toDomain() = Endereco(
        id = id, clienteId = clienteId, apelido = apelido, cep = cep,
        logradouro = logradouro, numero = numero, complemento = complemento,
        bairro = bairro, cidade = cidade, uf = uf, latitude = latitude,
        longitude = longitude, principal = principal
    )

    private fun Endereco.toEntity() = EnderecoEntity(
        id = id, clienteId = clienteId, apelido = apelido, cep = cep,
        logradouro = logradouro, numero = numero, complemento = complemento,
        bairro = bairro, cidade = cidade, uf = uf, latitude = latitude,
        longitude = longitude, principal = principal
    )
}
