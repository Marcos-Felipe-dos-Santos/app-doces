package com.confeitaria.gestao.domain.model.enums

enum class StatusPedido(val label: String) {
    PENDENTE("Pendente"),
    EM_PRODUCAO("Em Produção"),
    PRONTO("Pronto"),
    SAIU_ENTREGA("Saiu para Entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado")
}
