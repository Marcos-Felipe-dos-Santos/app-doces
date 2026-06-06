package com.confeitaria.gestao.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openMap(context: Context, endereco: String) {
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(endereco)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}
