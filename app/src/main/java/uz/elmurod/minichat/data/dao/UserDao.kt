package uz.elmurod.minichat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.elmurod.minichat.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("""SELECT * FROM users WHERE email= :email AND password= :password LIMIT 1""")
    suspend fun login(email: String,password: String): User?


}