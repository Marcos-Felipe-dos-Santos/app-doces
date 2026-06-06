package com.confeitaria.gestao.presentation.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDataBR(): String = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date(this))
fun Long.toDataHoraBR(): String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(Date(this))
fun Long.toDiaSemana(): String = SimpleDateFormat("EEEE", Locale("pt", "BR")).format(Date(this))
