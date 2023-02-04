package aej.dino.netflixcloneapps.ui.screen.dashboard
import aej.dino.netflixcloneapps.ui.Routers
import aej.dino.netflixcloneapps.ui.screen.dashboard.favorite.FavoriteScreen
import aej.dino.netflixcloneapps.ui.screen.dashboard.home.HomeScreen
import aej.dino.netflixcloneapps.ui.screen.dashboard.profile.ProfileScreen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@ExperimentalMaterial3Api
@Composable
fun DashboardScreenController(
    parentNavHostController: NavHostController,
    dashboardNavController: NavHostController
){
    NavHost(navController = dashboardNavController, startDestination = Routers.HOME){
        composable(
            route = Routers.HOME
        ) {
            HomeScreen(parentNavHostController)
        }

        composable(
            route = Routers.FAVORITE
        ) {
            FavoriteScreen(parentNavHostController)
        }

        composable(
            route = Routers.PROFILE
        ) {
            ProfileScreen(parentNavHostController)
        }
    }
}