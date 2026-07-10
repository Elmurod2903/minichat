package uz.elmurod.minichat.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import uz.elmurod.minichat.data.model.User

sealed class Resourse<out T> {
    object Loading : Resourse<Nothing>()
    data class Success<out T>(val data: T) : Resourse<T>()
    data class Error(val message: String) : Resourse<Nothing>()
}

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun registerUser(user: User, password: String): Resourse<Unit> {
        return try {

            val result = auth.createUserWithEmailAndPassword(user.email, password).await()
            val uid = result.user?.uid ?: return Resourse.Error("ID topilmadi")

            val finalUser = user.copy(uid = uid)
            db.collection("users").document(uid).set(finalUser.toMap()).await()
            Resourse.Success(Unit)
        } catch (e: Exception) {
            Resourse.Error(e.localizedMessage ?: "Xatolik yuz berdi")
        }
    }

    suspend fun loginUser(email: String, password: String): Resourse<User> {
        if (email.isBlank() || password.isBlank()){
            return Resourse.Error("Profil malumoti bo'sh")
        }
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return Resourse.Error("Foydalanuvchi topilmadi")

//            val document = db.collection("users").document(uid).get().await()
//            val user = document.toObject(User::class.java)
            val user = fetchUserProfile(uid)
            if (user != null) {
                Resourse.Success(user)
            } else {
                Resourse.Error("Profil malumotlari bo'sh")
            }
        } catch (e: Exception) {
            Resourse.Error(e.localizedMessage ?: "Login yoki Parol xato")

        }

    }
    suspend fun fetchUserProfile(uid: String): User? {
        return try {
            val document = db.collection("users").document(uid).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
    fun getCurrentUserUid(): String? = auth.currentUser?.uid

    fun signOut() {
        auth.signOut()
    }
}