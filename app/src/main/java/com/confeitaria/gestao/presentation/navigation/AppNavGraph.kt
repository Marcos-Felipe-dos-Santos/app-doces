package com.confeitaria.gestao.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.confeitaria.gestao.presentation.ui.catalogo.CatalogoScreen
import com.confeitaria.gestao.presentation.ui.catalogo.ProdutoFormScreen
import com.confeitaria.gestao.presentation.ui.clientes.ClienteDetalheScreen
import com.confeitaria.gestao.presentation.ui.clientes.ClienteFormScreen
import com.confeitaria.gestao.presentation.ui.clientes.ClientesScreen
import com.confeitaria.gestao.presentation.ui.configuracoes.ConfiguracoesScreen
import com.confeitaria.gestao.presentation.ui.dashboard.DashboardScreen
import com.confeitaria.gestao.presentation.ui.financeiro.FinanceiroScreen
import com.confeitaria.gestao.presentation.ui.novopedido.NovoPedidoScreen
import com.confeitaria.gestao.presentation.ui.pedidos.PedidoDetalheScreen
import com.confeitaria.gestao.presentation.ui.pedidos.PedidosScreen
import com.confeitaria.gestao.presentation.ui.relatorios.RelatoriosScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Triple(Screen.Dashboard, "Home", Icons.Default.Dashboard),
                    Triple(Screen.Clientes, "Clientes", Icons.Default.People),
                    Triple(Screen.Pedidos, "Pedidos", Icons.Default.ListAlt),
                    Triple(Screen.Catalogo, "Catálogo", Icons.Default.MenuBook),
                    Triple(Screen.Configuracoes, "Mais", Icons.Default.MoreHoriz)
                )
                items.forEach { (screen, label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentDestination?.route == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(navController)
            }
            composable(Screen.Clientes.route) {
                ClientesScreen(navController)
            }
            composable(
                route = Screen.ClienteDetalhe.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                ClienteDetalheScreen(navController)
            }
            composable(
                route = Screen.ClienteForm.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType; defaultValue = -1L })
            ) {
                ClienteFormScreen(navController)
            }
            composable(Screen.Pedidos.route) {
                PedidosScreen(navController)
            }
            composable(
                route = Screen.PedidoDetalhe.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                PedidoDetalheScreen(navController)
            }
            composable(
                route = Screen.NovoPedido.route,
                arguments = listOf(navArgument("pedidoId") { type = NavType.LongType; defaultValue = -1L })
            ) {
                NovoPedidoScreen(navController)
            }
            composable(Screen.Catalogo.route) {
                CatalogoScreen(navController)
            }
            composable(
                route = Screen.ProdutoForm.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType; defaultValue = -1L })
            ) {
                ProdutoFormScreen(navController)
            }
            composable(Screen.Financeiro.route) {
                FinanceiroScreen(navController)
            }
            composable(Screen.Relatorios.route) {
                RelatoriosScreen(navController)
            }
            composable(Screen.Configuracoes.route) {
                ConfiguracoesScreen(navController)
            }
        }
    }
}
