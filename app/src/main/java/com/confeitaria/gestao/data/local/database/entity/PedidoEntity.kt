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
    val totalProdutos: Double = 0.0,
    val totalFrete: Double = 0.0,
    val desconto: Double = 0.0,
    val totalFinal: Double = 0.0,
    val observacoes: String? = null,
    val dataCriacao: Long = System.currentTimeMillis()
)
