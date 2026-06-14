package com.confeitaria.gestao.data.local.database.dao

import androidx.room.*
import com.confeitaria.gestao.data.local.database.entity.PagamentoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PagamentoDao {
    @Query("SELECT * FROM pagamento WHERE pedidoId = :pedidoId")
    fun getByPedido(pedidoId: Long): Flow<List<PagamentoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pagamento: PagamentoEntity): Long

    @Query("SELECT * FROM pagamento WHERE confirmado = 0")
    fun getPendentes(): Flow<List<PagamentoEntity>>

    @Query("UPDATE pagamento SET confirmado = 1 WHERE id = :id")
    suspend fun confirmar(id: Long)

    @Query("SELECT COALESCE(SUM(valor), 0) FROM pagamento WHERE confirmado = 1 AND dataPagamento BETWEEN :inicio AND :fim")
    fun getTotalRecebidoPeriodo(inicio: Long, fim: Long): Flow<Long>
}
