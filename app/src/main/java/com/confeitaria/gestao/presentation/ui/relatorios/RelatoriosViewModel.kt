package com.confeitaria.gestao.presentation.ui.relatorios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.confeitaria.gestao.domain.repository.PedidoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

data class RelatorioMensalUiState(
    val totalPedidos: Int = 0,
    val faturamentoBruto: Long = 0L,
    val recebido: Long = 0L,
    val pendente: Long = 0L,
    val ticketMedio: Long = 0L
)

@HiltViewModel
class RelatoriosViewModel @Inject constructor(
    private val pedidoRepository: PedidoRepository
) : ViewModel() {

    private val periodoAtual: Pair<Long, Long> get() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val inicio = cal.timeInMillis
        cal.add(Calendar.MONTH, 1)
        cal.add(Calendar.MILLISECOND, -1)
        val fim = cal.timeInMillis
        return inicio to fim
    }

    val relatorio: StateFlow<RelatorioMensalUiState> = run {
        val (inicio, fim) = periodoAtual
        combine(
            pedidoRepository.getPedidosPeriodo(inicio, fim),
            pedidoRepository.getTotalRecebidoPeriodo(inicio, fim)
        ) { pedidos, recebido ->
            val faturamento = pedidos.sumOf { it.totalFinal }
            val count = pedidos.size
            RelatorioMensalUiState(
                totalPedidos = count,
                faturamentoBruto = faturamento,
                recebido = recebido,
                pendente = faturamento - recebido,
                ticketMedio = if (count > 0) faturamento / count else 0L
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RelatorioMensalUiState()
        )
    }
}
