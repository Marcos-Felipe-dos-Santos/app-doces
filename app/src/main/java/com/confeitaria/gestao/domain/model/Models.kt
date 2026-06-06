package com.confeitaria.gestao.domain.model

import com.confeitaria.gestao.domain.model.enums.*

data class Cliente(
    val id: Long = 0,
    val nome: String,
    val telefone: String,
    val email: String? = null,
    val observacoes: String? = null,
    val dataCadastro: Long = System.currentTimeMillis(),
    val enderecos: List<Endereco> = emptyList()
)

data class Endereco(
    val id: Long = 0,
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
) {
    val enderecoCompleto: String
        get() = "$logradouro, $numero${complemento?.let { ", $it" } ?: ""}, $bairro, $cidade - $uf"
}

data class Categoria(
    val id: Long = 0,
    val nome: String,
    val cor: String? = null
)

data class Produto(
    val id: Long = 0,
    val categoriaId: Long? = null,
    val nome: String,
    val descricao: String? = null,
    val precoBase: Double,
    val fotoPath: String? = null,
    val variacoes: List<ProdutoVariacao> = emptyList()
)

data class ProdutoVariacao(
    val id: Long = 0,
    val produtoId: Long,
    val nome: String,
    val precoAdicional: Double = 0.0
)

data class Pedido(
    val id: Long = 0,
    val clienteId: Long,
    val clienteNome: String = "",
    val dataPedido: Long = System.currentTimeMillis(),
    val dataEntrega: Long? = null,
    val horaEntrega: String? = null,
    val tipoEntrega: TipoEntrega,
    val enderecoId: Long? = null,
    val endereco: Endereco? = null,
    val status: StatusPedido = StatusPedido.PENDENTE,
    val formaPagamento: FormaPagamento? = null,
    val totalProdutos: Double = 0.0,
    val totalFrete: Double = 0.0,
    val desconto: Double = 0.0,
    val totalFinal: Double = 0.0,
    val observacoes: String? = null,
    val itens: List<ItemPedido> = emptyList(),
    val pagamentos: List<Pagamento> = emptyList()
)

data class ItemPedido(
    val id: Long = 0,
    val pedidoId: Long,
    val produtoId: Long,
    val produtoNome: String = "",
    val variacaoId: Long? = null,
    val variacaoNome: String? = null,
    val quantidade: Int = 1,
    val precoUnitario: Double,
    val observacoes: String? = null
) {
    val subtotal: Double get() = quantidade * precoUnitario
}

data class Pagamento(
    val id: Long = 0,
    val pedidoId: Long,
    val valor: Double,
    val formaPagamento: FormaPagamento,
    val dataPagamento: Long = System.currentTimeMillis(),
    val confirmado: Boolean = false
)
