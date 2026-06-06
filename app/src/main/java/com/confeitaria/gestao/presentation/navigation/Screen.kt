package com.confeitaria.gestao.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Clientes : Screen("clientes")
    object ClienteDetalhe : Screen("cliente/{id}") {
        fun createRoute(id: Long) = "cliente/$id"
    }
    object ClienteForm : Screen("cliente_form?id={id}") {
        fun createRoute(id: Long? = null) = if (id != null) "cliente_form?id=$id" else "cliente_form"
    }
    object Pedidos : Screen("pedidos")
    object PedidoDetalhe : Screen("pedido/{id}") {
        fun createRoute(id: Long) = "pedido/$id"
    }
    object NovoPedido : Screen("novo_pedido?pedidoId={pedidoId}") {
        fun createRoute(pedidoId: Long? = null) = if (pedidoId != null) "novo_pedido?pedidoId=$pedidoId" else "novo_pedido"
    }
    object Catalogo : Screen("catalogo")
    object ProdutoForm : Screen("produto_form?id={id}") {
        fun createRoute(id: Long? = null) = if (id != null) "produto_form?id=$id" else "produto_form"
    }
    object Financeiro : Screen("financeiro")
    object Relatorios : Screen("relatorios")
    object Configuracoes : Screen("configuracoes")
}
