package uz.elmurod.minichat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.elmurod.minichat.ui.screens.chat.ChatScreen
import uz.elmurod.minichat.ui.screens.group.GroupScreen
import uz.elmurod.minichat.ui.screens.home.HomeScreen
import uz.elmurod.minichat.ui.screens.profile.ProfileScreen
import uz.elmurod.minichat.ui.screens.register.RegisterScreen
import uz.elmurod.minichat.ui.screens.login.LoginScreen


@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route){
            LoginScreen(navController)
        }
        composable(Routes.Register.route){
            RegisterScreen(navController)
        }
        composable(Routes.Profile.route){
            ProfileScreen(navController)
        }
        composable(Routes.Chat.route){
            ChatScreen(navController)
        }
        composable(Routes.Home.route){
            HomeScreen(navController)
        }
        composable(Routes.Groups.route){
            GroupScreen(navController)
        }
    }
}