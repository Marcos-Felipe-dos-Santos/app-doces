package com.confeitaria.gestao.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun abrirWhatsApp(context: Context, telefone: String, mensagem: String = "") {
    val numero = telefone.filter { it.isDigit() }.let { if (it.startsWith("55")) it else "55$it" }
    val url = "https://wa.me/$numero" + if (mensagem.isNotBlank()) "?text=${Uri.encode(mensagem)}" else ""
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try { context.startActivity(intent) } catch (e: Exception) { /* sem WhatsApp instalado */ }
}

fun abrirMaps(context: Context, endereco: String) {
    val query = Uri.encode(endereco)
    val geoUri = Uri.parse("geo:0,0?q=$query")
    val intent = Intent(Intent.ACTION_VIEW, geoUri)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=$query"))
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try { context.startActivity(webIntent) } catch (e2: Exception) { /* sem app de mapas nem navegador */ }
    }
}
