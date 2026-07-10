package uz.elmurod.minichat.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.elmurod.minichat.data.model.User

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<Resourse<User>?>(null)
    val authState = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun checkCurrentUser(): String? {
        val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            // Agar foydalanuvchi tizimda bo'lsa, uning ma'lumotlarini Firestore-dan yuklab olamiz
            viewModelScope.launch {
                val profile = repository.fetchUserProfile(uid)
                if (profile != null) {
                    _currentUser.value = profile
                }
            }
        }
        return uid
    }

    fun register(user: User, password: String) {
        viewModelScope.launch {
            _authState.value = Resourse.Loading
            when (val result = repository.registerUser(user, password)) {
                is Resourse.Error -> {
                    _authState.value = Resourse.Error(result.message)
                }
                is Resourse.Success -> {
                    _authState.value = Resourse.Success(user)
                }
                else -> {}
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resourse.Loading
            val result = repository.loginUser(email, password)
            _authState.value = result
            if (result is Resourse.Success) {
                _currentUser.value = result.data
            }
        }
    }
    fun logout(){
        repository.signOut()
        _currentUser.value=null
        _authState.value=null
    }


}