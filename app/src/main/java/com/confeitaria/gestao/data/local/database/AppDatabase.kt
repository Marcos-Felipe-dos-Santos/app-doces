package com.confeitaria.gestao.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.confeitaria.gestao.data.local.database.dao.*
import com.confeitaria.gestao.data.local.database.entity.*

@Database(
    entities = [
        ClienteEntity::class,
        EnderecoEntity::class,
        CategoriaEntity::class,
        ProdutoEntity::class,
        ProdutoVariacaoEntity::class,
        PedidoEntity::class,
        ItemPedidoEntity::class,
        PagamentoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun enderecoDao(): EnderecoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun produtoDao(): ProdutoDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun itemPedidoDao(): ItemPedidoDao
    abstract fun pagamentoDao(): PagamentoDao
}
