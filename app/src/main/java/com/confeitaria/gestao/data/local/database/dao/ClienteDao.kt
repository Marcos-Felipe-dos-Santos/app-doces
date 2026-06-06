package com.confeitaria.gestao.data.local.database.dao

import androidx.room.*
import com.confeitaria.gestao.data.local.database.entity.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Query("SELECT * FROM cliente WHERE ativo = 1 ORDER BY nome ASC")
    fun getAll(): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM cliente WHERE nome LIKE '%' || :query || '%' AND ativo = 1")
    fun search(query: String): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM cliente WHERE id = :id")
    suspend fun getById(id: Long): ClienteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cliente: ClienteEntity): Long

    @Update
    suspend fun update(cliente: ClienteEntity)

    @Query("UPDATE cliente SET ativo = 0 WHERE id = :id")
    suspend fun delete(id: Long)
}
