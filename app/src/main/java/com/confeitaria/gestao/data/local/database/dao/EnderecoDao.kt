package com.confeitaria.gestao.data.local.database.dao

import androidx.room.*
import com.confeitaria.gestao.data.local.database.entity.EnderecoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EnderecoDao {
    @Query("SELECT * FROM endereco WHERE clienteId = :clienteId")
    fun getByCliente(clienteId: Long): Flow<List<EnderecoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(endereco: EnderecoEntity): Long

    @Delete
    suspend fun delete(endereco: EnderecoEntity)

    @Query("UPDATE endereco SET principal = 0 WHERE clienteId = :clienteId")
    suspend fun clearPrincipal(clienteId: Long)
}
