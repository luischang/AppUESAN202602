package dev.lchang.appuesan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object): aquí están las consultas SQL a la tabla de favoritos.
 * Las funciones que devuelven Flow se actualizan solas cuando cambia la tabla.
 */
@Dao
interface FavoriteCountryDao {

    // Todos los favoritos, ordenados por ranking FIFA
    @Query("SELECT * FROM favorite_countries ORDER BY ranking ASC")
    fun getAll(): Flow<List<FavoriteCountryEntity>>

    // Solo los nombres, para saber qué estrella pintar en la lista principal
    @Query("SELECT name FROM favorite_countries")
    fun getFavoriteNames(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: FavoriteCountryEntity)

    @Query("DELETE FROM favorite_countries WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("DELETE FROM favorite_countries")
    suspend fun deleteAll()
}
