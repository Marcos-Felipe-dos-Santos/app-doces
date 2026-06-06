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
            composable(Screen.Dashboard.route) { /* DashboardScreen() */ }
            composable(Screen.Clientes.route) { /* ClientesScreen() */ }
            composable(
                route = Screen.ClienteDetalhe.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { /* ClienteDetalheScreen() */ }
            composable(
                route = Screen.ClienteForm.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType; nullable = true; defaultValue = -1L })
            ) { /* ClienteFormScreen() */ }
            composable(Screen.Pedidos.route) { /* PedidosScreen() */ }
            composable(
                route = Screen.PedidoDetalhe.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { /* PedidoDetalheScreen() */ }
            composable(Screen.Catalogo.route) { /* CatalogoScreen() */ }
            composable(Screen.Configuracoes.route) { /* ConfiguracoesScreen() */ }
        }
    }
}
