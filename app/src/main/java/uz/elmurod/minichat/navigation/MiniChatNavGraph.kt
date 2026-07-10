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
    val authViewModel: AuthViewModel = viewModel()

    val currentUserUid = remember { authViewModel.checkCurrentUser() }

    val startDestination = if (currentUserUid != null) Routes.Main.route else Routes.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Login.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(Routes.Register.route) {
            RegisterScreen(navController, authViewModel)
        }
        composable(Routes.Main.route) {
            MainScreen(navController, authViewModel)
        }

    }
}