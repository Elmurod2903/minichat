package uz.elmurod.minichat.data.model


data class User(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val password: String
)
