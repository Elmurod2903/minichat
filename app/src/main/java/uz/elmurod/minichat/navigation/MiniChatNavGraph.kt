package uz.elmurod.minichat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.elmurod.minichat.auth.AuthViewModel
import uz.elmurod.minichat.ui.screens.MainScreen
import uz.elmurod.minichat.ui.screens.register.RegisterScreen
import uz.elmurod.minichat.ui.screens.login.LoginScreen


@Composable
fun MiniChatNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) { backStackEntry ->
            val authViewModel: AuthViewModel = viewModel()
            LoginScreen(navController, authViewModel)
        }
        composable(Routes.Register.route) { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry(Routes.Login.route) }
            val authViewModel: AuthViewModel = viewModel(parentEntry)

            RegisterScreen(navController, authViewModel)
        }
        composable(Routes.Main.route) {
            MainScreen()
        }

//        composable(Routes.Profile.route){
//            ProfileScreen(navController)
//        }
//        composable(Routes.Chat.route){
//            ChatScreen(navController)
//        }
//        composable(Routes.Home.route){
//            HomeScreen(navController)
//        }
//        composable(Routes.Groups.route){
//            GroupScreen(navController)
//        }
    }
}