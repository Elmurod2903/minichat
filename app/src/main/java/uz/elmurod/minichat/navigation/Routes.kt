package uz.elmurod.minichat.navigation

sealed class Routes(val route: String) {
    object Login: Routes("login")
    object Register: Routes("register")
    object Home: Routes("home")
    object Chat: Routes("chat")
    object Profile: Routes("profile")
    object Groups: Routes("groups")
}