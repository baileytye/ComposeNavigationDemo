package com.baileytye.composenavigationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.baileytye.composenavigationdemo.ui.AppNavigation
import com.baileytye.composenavigationdemo.ui.Graph
import com.baileytye.composenavigationdemo.ui.Screen
import com.baileytye.composenavigationdemo.ui.theme.ComposeNavigationDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Home()
        }
    }
}

@Composable
fun Home() {

    val navController = rememberNavController()

    //Selected bottom nav item
    val selectedGraph by navController.currentGraphAsState()

    //Can show/hide bottom bar given the destination
    val showBottomBar = navController.currentRoute()?.let { currentRoute ->
        listOf(
            Screen.A.createRoute(Graph.A),
            Screen.B.createRoute(Graph.B),
            Screen.C.createRoute(Graph.C)
        ).contains(currentRoute)
    } == true

    ComposeNavigationDemoTheme {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomAppBar(
                        selectedGraph = selectedGraph,
                        onItemClick = { graph ->
                            navController.navigate(graph.route) {
                                launchSingleTop = true
                                //For multi backstack
                                restoreState = true

                                popUpTo(navController.graph.findStartDestination().id) {
                                    //For multi backstack
                                    saveState = true
                                }
                            }
                        }
                    )
                }
            }) {
            AppNavigation(navController = navController)
        }
    }
}

@Composable
fun BottomAppBar(
    selectedGraph: Graph,
    onItemClick: (Graph) -> Unit
) {
    BottomNavigation {
        BottomNavigationItem(
            selected = selectedGraph.route == Graph.A.route,
            onClick = {
                onItemClick(Graph.A)
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    tint = if (selectedGraph.route == Graph.A.route) Color.Black else MaterialTheme.colors.onPrimary
                )
            })
        BottomNavigationItem(
            selected = selectedGraph.route == Graph.B.route,
            onClick = {
                onItemClick(Graph.B)
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null,
                    tint = if (selectedGraph.route == Graph.B.route) Color.Black else MaterialTheme.colors.onPrimary
                )
            })
        BottomNavigationItem(
            selected = selectedGraph.route == Graph.C.route,
            onClick = {
                onItemClick(Graph.C)
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = if (selectedGraph.route == Graph.C.route) Color.Black else MaterialTheme.colors.onPrimary
                )
            })
    }
}

@Composable
fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 * From Tivi
 */
@Stable
@Composable
private fun NavController.currentGraphAsState(): State<Graph> {
    val selectedItem = remember { mutableStateOf<Graph>(Graph.A) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Graph.A.route } -> {
                    selectedItem.value = Graph.A
                }
                destination.hierarchy.any { it.route == Graph.B.route } -> {
                    selectedItem.value = Graph.B
                }
                destination.hierarchy.any { it.route == Graph.C.route } -> {
                    selectedItem.value = Graph.C
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}