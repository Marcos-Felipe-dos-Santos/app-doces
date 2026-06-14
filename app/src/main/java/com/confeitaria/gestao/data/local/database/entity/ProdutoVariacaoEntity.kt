package com.confeitaria.gestao.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "produto_variacao",
    foreignKeys = [ForeignKey(entity = ProdutoEntity::class,
        parentColumns = ["id"], childColumns = ["produtoId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("produtoId")]
)
data class ProdutoVariacaoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val produtoId: Long,
    val nome: String,
    val precoAdicional: Long = 0L
)
