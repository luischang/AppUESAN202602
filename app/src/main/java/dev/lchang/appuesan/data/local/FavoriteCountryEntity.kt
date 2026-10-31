package dev.lchang.appuesan.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.lchang.appuesan.data.model.CountryModel

/**
 * Tabla "favorite_countries" en la base de datos SQLite local (Room).
 * Cada fila es un país que el usuario marcó como favorito.
 */
@Entity(tableName = "favorite_countries")
data class FavoriteCountryEntity(
    @PrimaryKey
    val name: String,
    val ranking: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "saved_at")
    val savedAt: Long = System.currentTimeMillis()
)

// Conversiones entre el modelo de la UI y la entidad de la base de datos
fun CountryModel.toEntity() = FavoriteCountryEntity(
    name = name,
    ranking = ranking,
    imageUrl = imageUrl
)

fun FavoriteCountryEntity.toModel() = CountryModel(
    name = name,
    ranking = ranking,
    imageUrl = imageUrl
)
