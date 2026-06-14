package com.confeitaria.gestao.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "item_pedido",
    foreignKeys = [
        ForeignKey(entity = PedidoEntity::class, parentColumns = ["id"],
            childColumns = ["pedidoId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = ProdutoEntity::class, parentColumns = ["id"],
            childColumns = ["produtoId"], onDelete = ForeignKey.RESTRICT),
        ForeignKey(entity = ProdutoVariacaoEntity::class, parentColumns = ["id"],
            childColumns = ["variacaoId"], onDelete = ForeignKey.SET_NULL)
    ],
    indices = [Index("pedidoId"), Index("produtoId"), Index("variacaoId")]
)
data class ItemPedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pedidoId: Long,
    val produtoId: Long,
    val variacaoId: Long? = null,
    val quantidade: Int = 1,
    val precoUnitario: Long,
    val observacoes: String? = null
)
