package com.confeitaria.gestao.presentation.ui.novopedido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Cliente
import com.confeitaria.gestao.domain.model.ItemPedido
import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.domain.model.Produto
import com.confeitaria.gestao.domain.model.enums.TipoEntrega
import com.confeitaria.gestao.domain.usecase.cliente.GetClientesUseCase
import com.confeitaria.gestao.domain.usecase.pedido.SavePedidoUseCase
import com.confeitaria.gestao.domain.usecase.produto.GetProdutosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NovoPedidoUiState(
    val clientes: List<Cliente> = emptyList(),
    val produtos: List<Produto> = emptyList(),
    val clienteSelecionado: Cliente? = null,
    val itens: List<ItemPedido> = emptyList(),
    val tipoEntrega: TipoEntrega = TipoEntrega.RETIRADA,
    val salvando: Boolean = false,
    val pedidoSalvoId: Long? = null
) {
    val totalProdutos: Double get() = itens.sumOf { it.subtotal }
    val podesSalvar: Boolean get() = clienteSelecionado != null && itens.isNotEmpty() && !salvando
}

@HiltViewModel
class NovoPedidoViewModel @Inject constructor(
    private val getClientesUseCase: GetClientesUseCase,
    private val getProdutosUseCase: GetProdutosUseCase,
    private val savePedidoUseCase: SavePedidoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NovoPedidoUiState())
    val uiState: StateFlow<NovoPedidoUiState> = _uiState.asStateFlow()

    init {
        getClientesUseCase()
            .onEach { clientes -> _uiState.update { it.copy(clientes = clientes) } }
            .launchIn(viewModelScope)

        getProdutosUseCase()
            .onEach { produtos -> _uiState.update { it.copy(produtos = produtos) } }
            .launchIn(viewModelScope)
    }

    fun selecionarCliente(cliente: Cliente) {
        _uiState.update { it.copy(clienteSelecionado = cliente) }
    }

    fun adicionarItem(produto: Produto, quantidade: Int) {
        val novoItem = ItemPedido(
            pedidoId = 0,
            produtoId = produto.id,
            produtoNome = produto.nome,
            quantidade = quantidade,
            precoUnitario = produto.precoBase
        )
        _uiState.update { it.copy(itens = it.itens + novoItem) }
    }

    fun removerItem(index: Int) {
        _uiState.update { state ->
            state.copy(itens = state.itens.toMutableList().also { it.removeAt(index) })
        }
    }

    fun setTipoEntrega(tipo: TipoEntrega) {
        _uiState.update { it.copy(tipoEntrega = tipo) }
    }

    fun salvar() {
        val state = _uiState.value
        val cliente = state.clienteSelecionado ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(salvando = true) }
            val total = state.totalProdutos
            val pedido = Pedido(
                clienteId = cliente.id,
                clienteNome = cliente.nome,
                tipoEntrega = state.tipoEntrega,
                totalProdutos = total,
                totalFinal = total,
                itens = state.itens
            )
            val id = savePedidoUseCase(pedido)
            _uiState.update { it.copy(salvando = false, pedidoSalvoId = id) }
        }
    }
}
