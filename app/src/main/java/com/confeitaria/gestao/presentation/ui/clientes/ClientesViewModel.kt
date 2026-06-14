package com.confeitaria.gestao.presentation.ui.clientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Cliente
import com.confeitaria.gestao.domain.usecase.cliente.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClientesUiState(
    val clientes: List<Cliente> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ClientesViewModel @Inject constructor(
    private val getClientesUseCase: GetClientesUseCase,
    private val searchClientesUseCase: SearchClientesUseCase,
    private val deleteClienteUseCase: DeleteClienteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _uiState = MutableStateFlow(ClientesUiState())
    val uiState: StateFlow<ClientesUiState> = _uiState.asStateFlow()

    init {
        _searchQuery
            .flatMapLatest { query ->
                if (query.isBlank()) getClientesUseCase() else searchClientesUseCase(query)
            }
            .onEach { list ->
                _uiState.update { it.copy(clientes = list, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onDelete(id: Long) {
        viewModelScope.launch {
            deleteClienteUseCase(id)
        }
    }
}
