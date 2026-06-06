package com.confeitaria.gestao.presentation.ui.catalogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Categoria
import com.confeitaria.gestao.domain.model.Produto
import com.confeitaria.gestao.domain.usecase.produto.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class CatalogoUiState(
    val produtos: List<Produto> = emptyList(),
    val categorias: List<Categoria> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class CatalogoViewModel @Inject constructor(
    private val getProdutosUseCase: GetProdutosUseCase,
    private val getCategoriasUseCase: GetCategoriasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        combine(getProdutosUseCase(), getCategoriasUseCase()) { produtos, categorias ->
            CatalogoUiState(produtos = produtos, categorias = categorias, isLoading = false)
        }.onEach { state ->
            _uiState.value = state
        }.launchIn(viewModelScope)
    }
}
