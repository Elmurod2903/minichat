package uz.elmurod.minichat.data.model


data class User(
    val uid: String = "",
    val firstName: String="",
    val lastName: String="",
    val email: String="",
    val phone: String="",
    val profileImageUrl: String=""
){
    fun toMap(): Map<String, Any>{
        return mapOf(
            "uid" to uid,
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phone" to phone,
            "profileImageUrl" to profileImageUrl,
        )
    }
}
