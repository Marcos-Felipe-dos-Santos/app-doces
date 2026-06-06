package com.confeitaria.gestao.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.confeitaria.gestao.domain.repository.PedidoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificacaoWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: PedidoRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val pedidoId = inputData.getLong("pedidoId", -1)
        if (pedidoId == -1L) return Result.failure()

        val pedido = repository.getById(pedidoId) ?: return Result.failure()

        val notification = NotificationCompat.Builder(applicationContext, "pedidos")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Lembrete de Pedido")
            .setContentText("Pedido #${pedido.id} para ${pedido.dataEntrega}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        try {
            NotificationManagerCompat.from(applicationContext).notify(pedidoId.toInt(), notification)
        } catch (e: SecurityException) {
            return Result.failure()
        }

        return Result.success()
    }
}
