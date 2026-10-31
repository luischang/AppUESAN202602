package dev.lchang.appuesan.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.lchang.appuesan.data.local.AppDatabase
import dev.lchang.appuesan.data.local.toEntity
import dev.lchang.appuesan.data.model.CountryModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).favoriteCountryDao()

    // Lista de países que se muestra al iniciar
    val countries: List<CountryModel> = mockCountries.sortedBy { it.ranking }

    // Nombres de los países guardados en Room (se actualiza automáticamente)
    val favoriteNames: StateFlow<Set<String>> = dao.getFavoriteNames()
        .map { it.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    // Si ya es favorito lo quita, si no lo guarda
    fun toggleFavorite(country: CountryModel) {
        viewModelScope.launch {
            if (favoriteNames.value.contains(country.name)) {
                dao.deleteByName(country.name)
            } else {
                dao.insert(country.toEntity())
            }
        }
    }
}
