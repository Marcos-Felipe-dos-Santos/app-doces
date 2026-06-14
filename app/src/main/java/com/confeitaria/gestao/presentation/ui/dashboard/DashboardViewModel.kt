package com.confeitaria.gestao.presentation.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Pedido
import com.confeitaria.gestao.domain.usecase.pedido.GetPedidosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class DashboardUiState(
    val pedidosHoje: List<Pedido> = emptyList(),
    val pedidosEmProducao: List<Pedido> = emptyList(),
    val totalMes: Long = 0L,
    val pagamentosPendentes: Int = 0,
    val isLoading: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getPedidosUseCase: GetPedidosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        getPedidosUseCase().onEach { pedidos ->
            _uiState.update { it.copy(pedidosHoje = pedidos, isLoading = false) }
        }.launchIn(viewModelScope)
    }
}
