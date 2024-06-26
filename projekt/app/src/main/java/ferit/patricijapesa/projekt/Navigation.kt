package ferit.patricijapesa.projekt

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ferit.patricijapesa.projekt.details.DetailsPage
import ferit.patricijapesa.projekt.home.MainScreen


object Routes {

    const val SCREEN_ALL_DRIVES = "driveList"
    const val SCREEN_DRIVE_DETAILS = "driveDetails/{driveId}"
    fun getDriveDetailsPath(driveId: String): String {
        return "driveDetails/$driveId"
    }
}

@Composable
fun NavigationController() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_ALL_DRIVES
    ) {
        composable(Routes.SCREEN_ALL_DRIVES) {
            MainScreen(navController)
        }
        composable(
            Routes.SCREEN_DRIVE_DETAILS,
            arguments = listOf(
                navArgument("driveId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("driveId")?.let { idFromArguments ->
                DetailsPage(
                    navController = navController,
                    driveId = idFromArguments
                )
            }
        }
    }
}