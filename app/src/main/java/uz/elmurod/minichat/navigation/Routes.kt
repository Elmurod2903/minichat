package uz.elmurod.minichat.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Main : Routes("main")

}
sealed class BottomNavItem(val route: String, val title: String,val icon: ImageVector){
    object Home : BottomNavItem("home","Home", Icons.Default.Home)
    object Chat : BottomNavItem("chat","Chat",Icons.AutoMirrored.Filled.Send)
    object Profile : BottomNavItem("profile","Profile",Icons.Default.Person)
    object Groups : BottomNavItem("groups","Groups", Icons.Default.DateRange)

}