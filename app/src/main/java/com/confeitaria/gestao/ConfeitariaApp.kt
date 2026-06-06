package com.confeitaria.gestao

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ConfeitariaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel("pedidos", "Pedidos", NotificationManager.IMPORTANCE_DEFAULT),
                NotificationChannel("pagamentos", "Pagamentos", NotificationManager.IMPORTANCE_DEFAULT),
                NotificationChannel("resumo", "Resumo Diário", NotificationManager.IMPORTANCE_LOW)
            )
            val manager = getSystemService(NotificationManager::class.java)
            channels.forEach { manager.createNotificationChannel(it) }
        }
    }
}
