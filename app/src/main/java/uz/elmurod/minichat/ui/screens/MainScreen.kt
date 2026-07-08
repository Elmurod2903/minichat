package uz.elmurod.minichat.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.elmurod.minichat.navigation.BottomNavItem
import uz.elmurod.minichat.ui.screens.chat.ChatScreen
import uz.elmurod.minichat.ui.screens.group.GroupScreen
import uz.elmurod.minichat.ui.screens.home.HomeScreen
import uz.elmurod.minichat.ui.screens.profile.ProfileScreen

@Composable
fun MainScreen() {
    val childNavController = rememberNavController()
    val items =
        listOf(BottomNavItem.Home, BottomNavItem.Chat, BottomNavItem.Groups, BottomNavItem.Profile)


    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by childNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            childNavController.navigate(item.route) {
                                popUpTo(childNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true

                            }

                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = childNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)

        ){
            composable(BottomNavItem.Home.route){ HomeScreen() }
            composable(BottomNavItem.Chat.route){ ChatScreen() }
            composable(BottomNavItem.Groups.route){ GroupScreen() }
            composable(BottomNavItem.Profile.route){ ProfileScreen() }
        }
    }
}