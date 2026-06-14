package com.confeitaria.gestao.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

// Worker desativado na Fase 1 — reativar na Fase 5 com HiltWorkerFactory
class NotificacaoWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = Result.success()
}
