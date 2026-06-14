package com.confeitaria.gestao.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pedido",
    foreignKeys = [
        ForeignKey(entity = ClienteEntity::class, parentColumns = ["id"],
            childColumns = ["clienteId"], onDelete = ForeignKey.RESTRICT),
        ForeignKey(entity = EnderecoEntity::class, parentColumns = ["id"],
            childColumns = ["enderecoId"], onDelete = ForeignKey.SET_NULL)
    ],
    indices = [Index("clienteId"), Index("enderecoId")]
)
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clienteId: Long,
    val dataPedido: Long = System.currentTimeMillis(),
    val dataEntrega: Long? = null,
    val horaEntrega: String? = null,
    val tipoEntrega: String,
    val enderecoId: Long? = null,
    val status: String = "PENDENTE",
    val formaPagamento: String? = null,
    val totalProdutos: Long = 0L,
    val totalFrete: Long = 0L,
    val desconto: Long = 0L,
    val totalFinal: Long = 0L,
    val observacoes: String? = null,
    val dataCriacao: Long = System.currentTimeMillis()
)
