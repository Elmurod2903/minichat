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

    fun register(user: User, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _authState.value = Resourse.Loading
            when (val result = repository.registerUser(user, password)) {
                is Resourse.Error -> {
                    _authState.value = Resourse.Error(result.message)
                }

                is Resourse.Success -> {
                    _authState.value = null
                    onSuccess()
                }

                else -> {}
            }
        }
    }


}