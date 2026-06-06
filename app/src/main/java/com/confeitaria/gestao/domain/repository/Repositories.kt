package com.confeitaria.gestao.domain.repository

import com.confeitaria.gestao.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ClienteRepository {
    fun getAll(): Flow<List<Cliente>>
    fun search(query: String): Flow<List<Cliente>>
    suspend fun getById(id: Long): Cliente?
    suspend fun save(cliente: Cliente): Long
    suspend fun delete(id: Long)
    suspend fun saveEndereco(endereco: Endereco): Long
    suspend fun deleteEndereco(endereco: Endereco)
}

interface ProdutoRepository {
    fun getAll(): Flow<List<Produto>>
    fun getCategorias(): Flow<List<Categoria>>
    suspend fun getById(id: Long): Produto?
    suspend fun save(produto: Produto): Long
    suspend fun delete(id: Long)
    suspend fun saveCategoria(categoria: Categoria): Long
    suspend fun deleteCategoria(categoria: Categoria)
}

interface PedidoRepository {
    fun getAll(): Flow<List<Pedido>>
    fun getByStatus(status: String): Flow<List<Pedido>>
    suspend fun getById(id: Long): Pedido?
    suspend fun save(pedido: Pedido): Long
    suspend fun updateStatus(id: Long, status: String)
    fun countAtivos(): Flow<Int>
    fun getReceitaPeriodo(inicio: Long, fim: Long): Flow<Double>
}

interface FreteRepository {
    suspend fun buscarCep(cep: String): Endereco?
    suspend fun geocodificar(endereco: String): Pair<Double, Double>?
    suspend fun calcularDistancia(origem: Pair<Double, Double>, destino: Pair<Double, Double>): Double?
}

interface ConfiguracaoRepository {
    fun getPrecoPorKm(): Flow<Double>
    suspend fun setPrecoPorKm(valor: Double)
    fun getNomeConfeiteira(): Flow<String>
    suspend fun setNomeConfeiteira(nome: String)
    fun getPixKey(): Flow<String>
    suspend fun setPixKey(key: String)
    fun getEnderecoBase(): Flow<String>
    suspend fun setEnderecoBase(endereco: String)
}
