package com.confeitaria.gestao.data.local.database.dao

import androidx.room.*
import com.confeitaria.gestao.data.local.database.entity.ProdutoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM produto WHERE ativo = 1 ORDER BY nome ASC")
    fun getAll(): Flow<List<ProdutoEntity>>

    @Query("SELECT * FROM produto WHERE categoriaId = :categoriaId AND ativo = 1")
    fun getByCategoria(categoriaId: Long): Flow<List<ProdutoEntity>>

    @Query("SELECT * FROM produto WHERE id = :id")
    suspend fun getById(id: Long): ProdutoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(produto: ProdutoEntity): Long

    @Update
    suspend fun update(produto: ProdutoEntity)

    @Query("UPDATE produto SET ativo = 0 WHERE id = :id")
    suspend fun delete(id: Long)
}
