package com.confeitaria.gestao.presentation.ui.clientes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Cliente
import com.confeitaria.gestao.domain.usecase.cliente.GetClienteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteDetalheViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getClienteByIdUseCase: GetClienteByIdUseCase
) : ViewModel() {

    private val clienteId: Long = savedStateHandle.get<Long>("id") ?: -1L

    private val _cliente = MutableStateFlow<Cliente?>(null)
    val cliente: StateFlow<Cliente?> = _cliente.asStateFlow()

    init {
        viewModelScope.launch {
            _cliente.value = getClienteByIdUseCase(clienteId)
        }
    }
}
