package com.confeitaria.gestao.presentation.ui.clientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Cliente
import com.confeitaria.gestao.domain.usecase.cliente.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClientesUiState(
    val clientes: List<Cliente> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class ClientesViewModel @Inject constructor(
    private val getClientesUseCase: GetClientesUseCase,
    private val deleteClienteUseCase: DeleteClienteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientesUiState())
    val uiState: StateFlow<ClientesUiState> = _uiState.asStateFlow()

    init {
        loadClientes()
    }

    private fun loadClientes() {
        getClientesUseCase().onEach { list ->
            _uiState.update { it.copy(clientes = list, isLoading = false) }
        }.launchIn(viewModelScope)
    }

    fun onSearch(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        // Implementar busca real se necessário
    }

    fun onDelete(id: Long) {
        viewModelScope.launch {
            deleteClienteUseCase(id)
        }
    }
}
