package dev.lchang.appuesan.presentation.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.lchang.appuesan.data.local.AppDatabase
import dev.lchang.appuesan.data.local.FavoriteCountryEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).favoriteCountryDao()

    // Lee los favoritos guardados en SQLite (Room) y se actualiza solo
    val favorites: StateFlow<List<FavoriteCountryEntity>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun remove(country: FavoriteCountryEntity) {
        viewModelScope.launch { dao.deleteByName(country.name) }
    }

    fun removeAll() {
        viewModelScope.launch { dao.deleteAll() }
    }
}
