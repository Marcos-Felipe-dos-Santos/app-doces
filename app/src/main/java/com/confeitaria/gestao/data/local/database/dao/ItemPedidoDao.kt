package com.confeitaria.gestao.data.local.database.dao

import androidx.room.*
import com.confeitaria.gestao.data.local.database.entity.ItemPedidoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemPedidoDao {
    @Query("SELECT * FROM item_pedido WHERE pedidoId = :pedidoId")
    fun getByPedido(pedidoId: Long): Flow<List<ItemPedidoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(itens: List<ItemPedidoEntity>)

    @Query("DELETE FROM item_pedido WHERE pedidoId = :pedidoId")
    suspend fun deleteByPedido(pedidoId: Long)
}
