package com.confeitaria.gestao.di

import com.confeitaria.gestao.data.repository.*
import com.confeitaria.gestao.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton abstract fun bindClienteRepository(impl: ClienteRepositoryImpl): ClienteRepository
    @Binds @Singleton abstract fun bindProdutoRepository(impl: ProdutoRepositoryImpl): ProdutoRepository
    @Binds @Singleton abstract fun bindPedidoRepository(impl: PedidoRepositoryImpl): PedidoRepository
    @Binds @Singleton abstract fun bindFreteRepository(impl: FreteRepositoryImpl): FreteRepository
}
