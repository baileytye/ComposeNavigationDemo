package com.baileytye.composenavigationdemo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.baileytye.composenavigationdemo.ui.screens.A
import com.baileytye.composenavigationdemo.ui.screens.B
import com.baileytye.composenavigationdemo.ui.screens.C
import com.baileytye.composenavigationdemo.ui.screens.details.ADetails

//Graphs are similar to nested navigation from XML based navigation
sealed class Graph(val route: String) {
    object A : Graph("a")
    object B : Graph("b")
    object C : Graph("c")
}

sealed class Screen(private val route: String) {
    fun createRoute(rootGraph: Graph) = "${rootGraph.route}/$route"

    object A : Screen("a")
    object B : Screen("b")
    object C : Screen("c")

    object ADetails : Screen("a/{id}") {
        fun createRoute(rootGraph: Graph, id: String): String {
            return "${rootGraph.route}/a/$id"
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(modifier = modifier, navController = navController, startDestination = Graph.A.route) {

        //Graph for bottom nav A
        navigation(startDestination = Screen.A.createRoute(Graph.A), route = Graph.A.route) {
            composable(Screen.A.createRoute(Graph.A)) {
                A(navigateToDetails = { id ->
                    navController.navigate(Screen.ADetails.createRoute(Graph.A, id))
                })
            }
            composable(
                route = Screen.ADetails.createRoute(Graph.A),
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                ADetails(idFromConstructor = backStackEntry.arguments?.getString("id")!!) {
                    navController.navigateUp()
                }
            }
        }

        //Graph for bottom nav B
        navigation(startDestination = Screen.B.createRoute(Graph.B), route = Graph.B.route) {
            composable(Screen.B.createRoute(Graph.B)) {
                B()
            }
        }

        //Graph for bottom nav C
        navigation(startDestination = Screen.C.createRoute(Graph.C), route = Graph.C.route) {
            composable(Screen.C.createRoute(Graph.C)) {
                C()
            }
        }
    }
}
