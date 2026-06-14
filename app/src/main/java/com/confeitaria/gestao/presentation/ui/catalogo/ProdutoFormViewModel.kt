package com.confeitaria.gestao.presentation.ui.catalogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.model.Categoria
import com.confeitaria.gestao.domain.model.Produto
import com.confeitaria.gestao.domain.usecase.produto.GetCategoriasUseCase
import com.confeitaria.gestao.domain.usecase.produto.SaveProdutoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProdutoFormViewModel @Inject constructor(
    private val saveProdutoUseCase: SaveProdutoUseCase,
    private val getCategoriasUseCase: GetCategoriasUseCase
) : ViewModel() {

    val categorias: StateFlow<List<Categoria>> = getCategoriasUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _salvo = MutableStateFlow(false)
    val salvo: StateFlow<Boolean> = _salvo.asStateFlow()

    fun salvar(nome: String, descricao: String, precoBase: Double, categoriaId: Long?) {
        viewModelScope.launch {
            saveProdutoUseCase(
                Produto(
                    nome = nome,
                    descricao = descricao.takeIf { it.isNotBlank() },
                    precoBase = precoBase,
                    categoriaId = categoriaId
                )
            )
            _salvo.value = true
        }
    }
}
