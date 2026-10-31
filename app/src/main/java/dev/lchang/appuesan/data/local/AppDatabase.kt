package dev.lchang.appuesan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos local de la app (un archivo SQLite llamado "appuesan.db").
 * Se usa un singleton para no abrir varias conexiones a la vez.
 */
@Database(
    entities = [FavoriteCountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteCountryDao(): FavoriteCountryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appuesan.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
