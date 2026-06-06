package com.confeitaria.gestao.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "produto",
    foreignKeys = [ForeignKey(entity = CategoriaEntity::class,
        parentColumns = ["id"], childColumns = ["categoriaId"], onDelete = ForeignKey.SET_NULL)],
    indices = [Index("categoriaId")]
)
data class ProdutoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoriaId: Long? = null,
    val nome: String,
    val descricao: String? = null,
    val precoBase: Double,
    val fotoPath: String? = null,
    val ativo: Boolean = true
)
