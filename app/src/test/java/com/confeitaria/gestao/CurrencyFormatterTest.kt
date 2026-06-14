package com.confeitaria.gestao

import com.confeitaria.gestao.presentation.util.toCentavos
import com.confeitaria.gestao.presentation.util.toMoedaBR
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyFormatterTest {

    @Test
    fun test_1250_centavos_to_moedaBR() {
        assertEquals("R$ 12,50", 1250L.toMoedaBR())
    }

    @Test
    fun test_0_centavos() {
        assertEquals("R$ 0,00", 0L.toMoedaBR())
    }

    @Test
    fun test_5_centavos() {
        assertEquals("R$ 0,05", 5L.toMoedaBR())
    }

    @Test
    fun test_string_to_centavos_virgula() {
        assertEquals(1250L, "12,50".toCentavos())
    }

    @Test
    fun test_string_to_centavos_ponto() {
        assertEquals(1250L, "12.50".toCentavos())
    }

    @Test
    fun test_string_vazia_to_centavos() {
        assertEquals(0L, "".toCentavos())
    }
}
