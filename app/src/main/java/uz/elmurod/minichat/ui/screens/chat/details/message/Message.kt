package uz.elmurod.minichat.ui.screens.chat.details.message

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class Message(
    val sendId: String = "",
    val receiverId: String = "",
    val messageText: String = "",
    @ServerTimestamp
    val timestamp: Date? = null
)
