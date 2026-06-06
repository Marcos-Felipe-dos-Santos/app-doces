package com.confeitaria.gestao.presentation.ui.pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.domain.usecase.pedido.GetPedidosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class PedidosUiState(
    val pedidos: List<Pedido> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class PedidosViewModel @Inject constructor(
    private val getPedidosUseCase: GetPedidosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PedidosUiState())
    val uiState: StateFlow<PedidosUiState> = _uiState.asStateFlow()

    init {
        loadPedidos()
    }

    private fun loadPedidos() {
        getPedidosUseCase().onEach { list ->
            _uiState.update { it.copy(pedidos = list, isLoading = false) }
        }.launchIn(viewModelScope)
    }
}
