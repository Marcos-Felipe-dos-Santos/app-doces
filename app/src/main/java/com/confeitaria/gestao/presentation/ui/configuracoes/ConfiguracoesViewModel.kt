package com.confeitaria.gestao.presentation.ui.configuracoes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.data.local.preferences.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConfiguracoesUiState(
    val nomeConfeiteira: String = "",
    val pixKey: String = "",
    val precoPorKm: Double = 1.50,
    val enderecoBase: String = ""
)

@HiltViewModel
class ConfiguracoesViewModel @Inject constructor(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfiguracoesUiState())
    val uiState: StateFlow<ConfiguracoesUiState> = _uiState.asStateFlow()

    init {
        preferences.nomeConfeiteira.onEach { nome ->
            _uiState.update { it.copy(nomeConfeiteira = nome) }
        }.launchIn(viewModelScope)

        preferences.pixKey.onEach { key ->
            _uiState.update { it.copy(pixKey = key) }
        }.launchIn(viewModelScope)

        preferences.precoPorKm.onEach { preco ->
            _uiState.update { it.copy(precoPorKm = preco) }
        }.launchIn(viewModelScope)

        preferences.enderecoBase.onEach { end ->
            _uiState.update { it.copy(enderecoBase = end) }
        }.launchIn(viewModelScope)
    }

    fun saveConfig(nome: String, pix: String, preco: Double, endereco: String) {
        viewModelScope.launch {
            preferences.setNomeConfeiteira(nome)
            preferences.setPixKey(pix)
            preferences.setPrecoPorKm(preco)
            preferences.setEnderecoBase(endereco)
        }
    }
}
