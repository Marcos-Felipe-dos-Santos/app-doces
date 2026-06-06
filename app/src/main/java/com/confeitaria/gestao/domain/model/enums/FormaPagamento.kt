package com.confeitaria.gestao.domain.model.enums

enum class FormaPagamento(val label: String) {
    DINHEIRO("Dinheiro"),
    PIX("PIX"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito")
}
