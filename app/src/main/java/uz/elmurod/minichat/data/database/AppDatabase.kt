package uz.elmurod.minichat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.elmurod.minichat.data.dao.UserDao
import uz.elmurod.minichat.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}