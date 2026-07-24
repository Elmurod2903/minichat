package uz.elmurod.minichat.ui.screens.chat.details.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import uz.elmurod.minichat.data.model.User

class MessageViewModel : ViewModel() {
    private val repository = MessageRepository()

    private val _message = MutableStateFlow<List<Message>>(emptyList())
    val message = _message.asStateFlow()

    private val _receiverUser = MutableStateFlow<User?>(null)
    val receiverUser = _receiverUser.asStateFlow()

    val currentId = repository.currentUid

    fun listenToMessage(receiverId: String) {
        viewModelScope.launch {
            repository.getMessage(receiverId)
                .catch { _message.value = emptyList() }
                .collect { messageList ->
                    _message.value = messageList
                }
        }

    }

    fun sendMessage(receiverId: String, text: String) {
        repository.sendMessage(receiverId, text)
    }

    fun loadReceiverInfo(receiverId: String) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(receiverId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _receiverUser.value = document.toObject(User::class.java)
                }
            }
    }

}