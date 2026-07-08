package uz.elmurod.minichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import uz.elmurod.minichat.navigation.MiniChatNavGraph
import uz.elmurod.minichat.ui.theme.MiniChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniChatTheme {
                MiniChatNavGraph()
            }
        }
    }
}
