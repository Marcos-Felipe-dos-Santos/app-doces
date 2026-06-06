package com.confeitaria.gestao.data.repository

import com.confeitaria.gestao.data.local.database.dao.CategoriaDao
import com.confeitaria.gestao.data.local.database.dao.ProdutoDao
import com.confeitaria.gestao.data.local.database.entity.CategoriaEntity
import com.confeitaria.gestao.data.local.database.entity.ProdutoEntity
import com.confeitaria.gestao.domain.model.Categoria
import com.confeitaria.gestao.domain.model.Produto
import com.confeitaria.gestao.domain.repository.ProdutoRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProdutoRepositoryImpl @Inject constructor(
    private val produtoDao: ProdutoDao,
    private val categoriaDao: CategoriaDao
) : ProdutoRepository {

    override fun getAll(): Flow<List<Produto>> = produtoDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    override fun getCategorias(): Flow<List<Categoria>> = categoriaDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getById(id: Long): Produto? = produtoDao.getById(id)?.toDomain()

    override suspend fun save(produto: Produto): Long = produtoDao.insert(produto.toEntity())

    override suspend fun delete(id: Long) = produtoDao.delete(id)

    override suspend fun saveCategoria(categoria: Categoria): Long = categoriaDao.insert(categoria.toEntity())

    override suspend fun deleteCategoria(categoria: Categoria) = categoriaDao.delete(categoria.toEntity())

    private fun ProdutoEntity.toDomain() = Produto(
        id = id, categoriaId = categoriaId, nome = nome,
        descricao = descricao, precoBase = precoBase, fotoPath = fotoPath
    )

    private fun Produto.toEntity() = ProdutoEntity(
        id = id, categoriaId = categoriaId, nome = nome,
        descricao = descricao, precoBase = precoBase, fotoPath = fotoPath
    )

    private fun CategoriaEntity.toDomain() = Categoria(id = id, nome = nome, cor = cor)

    private fun Categoria.toEntity() = CategoriaEntity(id = id, nome = nome, cor = cor)
}
