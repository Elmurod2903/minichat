package uz.elmurod.minichat.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.elmurod.minichat.auth.Resourse
import uz.elmurod.minichat.data.model.User

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _userState = MutableStateFlow<Resourse<List<User>>>(Resourse.Loading)


    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _userState.value = Resourse.Loading
            _userState.value = repository.getAllUsers()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    val filteredUsers = combine(_searchQuery.debounce(300), _userState) { query, userState ->
        when (userState) {
            is Resourse.Error -> Resourse.Error(userState.message)
            is Resourse.Loading -> Resourse.Loading
            is Resourse.Success -> {
                if (query.isBlank()) {
                    Resourse.Success(userState.data)
                } else {
                    val filtered = userState.data.filter { user ->
                        val firstName =
                            user.firstName.lowercase().contains(query.lowercase())
                        val lastname =
                            user.firstName.lowercase().contains(query.lowercase())
                        val email = user.firstName.lowercase().contains(query.lowercase())

                        firstName || lastname || email
                    }
                    Resourse.Success(filtered)
                }

            }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resourse.Loading
    )

}