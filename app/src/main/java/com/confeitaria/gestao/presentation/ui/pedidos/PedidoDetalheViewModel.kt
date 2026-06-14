package com.confeitaria.gestao.presentation.ui.pedidos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.domain.usecase.pedido.GetPedidoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PedidoDetalheViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPedidoByIdUseCase: GetPedidoByIdUseCase
) : ViewModel() {

    private val pedidoId: Long = savedStateHandle.get<Long>("id") ?: -1L

    private val _pedido = MutableStateFlow<Pedido?>(null)
    val pedido: StateFlow<Pedido?> = _pedido.asStateFlow()

    init {
        viewModelScope.launch {
            _pedido.value = getPedidoByIdUseCase(pedidoId)
        }
    }
}
