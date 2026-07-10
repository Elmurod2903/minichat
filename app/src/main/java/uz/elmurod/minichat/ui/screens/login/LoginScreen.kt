package uz.elmurod.minichat.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.rpc.context.AttributeContext
import uz.elmurod.minichat.auth.AuthViewModel
import uz.elmurod.minichat.auth.Resourse
import uz.elmurod.minichat.navigation.Routes

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is Resourse.Success) {
            navController.navigate(Routes.Main.route) {
                popUpTo(Routes.Login.route) { inclusive = true }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("MiniChat", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            }, label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            }, label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        if (authState is Resourse.Loading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (email.trim().isNotEmpty() && password.trim().isNotEmpty()) {
                        viewModel.login(email.trim(), password.trim())
                    }
                },
                enabled = email.trim().isNotEmpty() && password.trim().isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            Text(
                text = "Iltimos, barcha maydonlarni to'ldiring",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        if (authState is Resourse.Error) {
            Text(
                (authState as Resourse.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        TextButton(onClick = {
            navController.navigate(Routes.Register.route)
        }) {
            Text(
                "Don't have an account? Register",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}