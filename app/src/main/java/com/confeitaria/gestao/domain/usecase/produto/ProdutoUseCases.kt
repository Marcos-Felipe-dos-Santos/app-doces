package com.confeitaria.gestao.domain.usecase.produto

import com.confeitaria.gestao.domain.model.Categoria
import com.confeitaria.gestao.domain.model.Produto
import com.confeitaria.gestao.domain.repository.ProdutoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProdutosUseCase @Inject constructor(private val repository: ProdutoRepository) {
    operator fun invoke(): Flow<List<Produto>> = repository.getAll()
}

class SaveProdutoUseCase @Inject constructor(private val repository: ProdutoRepository) {
    suspend operator fun invoke(produto: Produto): Long = repository.save(produto)
}

class GetCategoriasUseCase @Inject constructor(private val repository: ProdutoRepository) {
    operator fun invoke(): Flow<List<Categoria>> = repository.getCategorias()
}
