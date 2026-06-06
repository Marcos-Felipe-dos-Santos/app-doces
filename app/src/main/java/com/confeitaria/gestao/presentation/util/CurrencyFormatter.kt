package com.confeitaria.gestao.presentation.util

import java.text.NumberFormat
import java.util.Locale

fun Double.toMoedaBR(): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)
