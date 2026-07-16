package uz.elmurod.minichat.ui.screens.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uz.elmurod.minichat.auth.Resourse
import uz.elmurod.minichat.data.model.User

class ChatRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getAllUsers(): Resourse<List<User>> {
      return  try {
            val currentUid = auth.currentUser?.uid ?: return Resourse.Error("Tizimga kiritilmagan")

            val collection = db.collection("users").get().await()
            val userList = collection.toObjects(User::class.java)

            val filteredList = userList.filter { it.uid != currentUid }
            Resourse.Success(filteredList)
        } catch (e: Exception) {
            Resourse.Error(e.localizedMessage ?: "Foydalanuvchilarni yuklashda xatolik ")
        }
    }
}