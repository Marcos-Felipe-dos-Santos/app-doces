package com.confeitaria.gestao.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pagamento",
    foreignKeys = [ForeignKey(entity = PedidoEntity::class, parentColumns = ["id"],
        childColumns = ["pedidoId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("pedidoId")]
)
data class PagamentoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pedidoId: Long,
    val valor: Long,
    val formaPagamento: String,
    val dataPagamento: Long = System.currentTimeMillis(),
    val confirmado: Boolean = false
)
