package uz.elmurod.minichat.ui.screens.chat.details.message

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MessageRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val currentUid: String get() = auth.currentUser?.uid ?: ""

    fun getChatId(receiverId: String): String {
        return if (currentUid < receiverId) {
            "${currentUid}_$receiverId"
        } else {
            "${receiverId}_$currentUid"

        }
    }

    fun sendMessage(receiverId: String, text: String) {
        if (text.isBlank() || currentUid.isEmpty()) return
        val chatId = getChatId(receiverId)

        val message = Message(
            sendId = currentUid,
            receiverId = receiverId,
            messageText = text.trim()
        )
        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)

    }

    fun getMessage(receiverId: String): Flow<List<Message>> = callbackFlow {
        val chatId = getChatId(receiverId)

        val query = db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val messages=snapshot?.toObjects(Message::class.java)?:emptyList()
            trySend(messages)

        }
        awaitClose { listener.remove() }
    }
}