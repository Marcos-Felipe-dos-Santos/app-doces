package com.confeitaria.gestao.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente")
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val telefone: String,
    val email: String? = null,
    val observacoes: String? = null,
    val dataCadastro: Long = System.currentTimeMillis(),
    val ativo: Boolean = true
)
