package com.confeitaria.gestao.presentation.util

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs

fun Long.toMoedaBR(): String = "R$ %d,%02d".format(this / 100, abs(this % 100).toInt())

fun Long.toMoedaBRInput(): String = "%d,%02d".format(this / 100, abs(this % 100).toInt())

fun String.toCentavos(): Long {
    if (this.isBlank()) return 0L
    val normalized = this.trim().replace(",", ".")
    val value = normalized.toBigDecimalOrNull() ?: return 0L
    return (value * BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).toLong()
}
