package com.example.evaluation4_android.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.evaluation4_android.R
import com.example.evaluation4_android.ui.ecrans.EcranListeTaches
import com.example.evaluation4_android.ui.ecrans.EcranAjoutTache
import com.example.evaluation4_android.ui.ecrans.EcranDetailModificationTache

const val ROUTE_LISTE_TACHES = "liste_taches"
const val ROUTE_AJOUT_TACHE = "ajout_tache"
const val ROUTE_DETAIL_TACHE = "detail_tache"
const val ARG_ID_TACHE = "idTache"

@Composable
fun NavigationApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_LISTE_TACHES,
        modifier = modifier
    ) {
        composable(route = ROUTE_LISTE_TACHES) {
            EcranListeTaches(
                onNaviguerVersAjoutTache = {
                    navController.navigate(ROUTE_AJOUT_TACHE)
                },
                onNaviguerVersDetailTache = { idTache ->
                    navController.navigate("$ROUTE_DETAIL_TACHE/$idTache")
                }
            )
        }

        composable(route = ROUTE_AJOUT_TACHE) {
            EcranAjoutTache(
                onNavigateUp = { navController.popBackStack() }
            )
        }

        composable(
            route = "$ROUTE_DETAIL_TACHE/{$ARG_ID_TACHE}",
            arguments = listOf(navArgument(ARG_ID_TACHE) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idTache = backStackEntry.arguments?.getInt(ARG_ID_TACHE)
            if (idTache != null) {
                EcranDetailModificationTache(
                    idTache = idTache,
                    onNavigateUp = { navController.popBackStack() }
                )
            } else {
                Text(text = stringResource(R.string.erreur_id_tache_invalide))

            }
        }
    }
}