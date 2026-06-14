package com.confeitaria.gestao.presentation.ui.clientes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Cliente
import com.confeitaria.gestao.domain.usecase.cliente.GetClienteByIdUseCase
import com.confeitaria.gestao.domain.usecase.cliente.SaveClienteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveClienteUseCase: SaveClienteUseCase,
    private val getClienteByIdUseCase: GetClienteByIdUseCase
) : ViewModel() {

    private val clienteId: Long = savedStateHandle.get<Long>("id") ?: -1L
    val isEditing: Boolean get() = clienteId != -1L

    private val _clienteInicial = MutableStateFlow<Cliente?>(null)
    val clienteInicial: StateFlow<Cliente?> = _clienteInicial.asStateFlow()

    private val _salvo = MutableStateFlow(false)
    val salvo: StateFlow<Boolean> = _salvo.asStateFlow()

    init {
        if (isEditing) {
            viewModelScope.launch {
                _clienteInicial.value = getClienteByIdUseCase(clienteId)
            }
        }
    }

    fun salvar(nome: String, telefone: String, email: String, observacoes: String) {
        viewModelScope.launch {
            saveClienteUseCase(
                Cliente(
                    id = if (isEditing) clienteId else 0L,
                    nome = nome,
                    telefone = telefone,
                    email = email.takeIf { it.isNotBlank() },
                    observacoes = observacoes.takeIf { it.isNotBlank() }
                )
            )
            _salvo.value = true
        }
    }
}
