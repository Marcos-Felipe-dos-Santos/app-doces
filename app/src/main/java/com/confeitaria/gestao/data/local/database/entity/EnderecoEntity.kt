package com.confeitaria.gestao.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "endereco",
    foreignKeys = [ForeignKey(entity = ClienteEntity::class,
        parentColumns = ["id"], childColumns = ["clienteId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("clienteId")]
)
data class EnderecoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clienteId: Long,
    val apelido: String? = null,
    val cep: String? = null,
    val logradouro: String,
    val numero: String,
    val complemento: String? = null,
    val bairro: String? = null,
    val cidade: String,
    val uf: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val principal: Boolean = false
)
