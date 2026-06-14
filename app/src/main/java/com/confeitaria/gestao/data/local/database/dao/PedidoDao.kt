package com.confeitaria.gestao.data.local.database.dao

import androidx.room.*
import com.confeitaria.gestao.data.local.database.entity.PedidoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Query("SELECT * FROM pedido ORDER BY dataEntrega DESC")
    fun getAll(): Flow<List<PedidoEntity>>

    @Query("SELECT * FROM pedido WHERE status = :status ORDER BY dataEntrega ASC")
    fun getByStatus(status: String): Flow<List<PedidoEntity>>

    @Query("SELECT * FROM pedido WHERE id = :id")
    suspend fun getById(id: Long): PedidoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pedido: PedidoEntity): Long

    @Update
    suspend fun update(pedido: PedidoEntity)

    @Query("UPDATE pedido SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Query("SELECT COUNT(*) FROM pedido WHERE status IN ('PENDENTE', 'EM_PRODUCAO', 'PRONTO')")
    fun countAtivos(): Flow<Int>

    @Query("SELECT SUM(totalFinal) FROM pedido WHERE status = 'ENTREGUE' AND dataEntrega BETWEEN :inicio AND :fim")
    fun getReceitaPeriodo(inicio: Long, fim: Long): Flow<Long?>
}
