package dev.lchang.appuesan.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import dev.lchang.appuesan.data.model.CountryModel


val mockCountries = listOf(
    CountryModel("Perú", 50,"https://flagcdn.com/w320/pe.png"),
    CountryModel("Brasil", 11,"https://flagcdn.com/w320/br.png"),
    CountryModel("España", 1,"https://flagcdn.com/w320/es.png"),
    CountryModel("Portugal", 7,"https://flagcdn.com/w320/pt.png"),
    CountryModel("Argentina", 4,"https://flagcdn.com/w320/ar.png"),
    CountryModel("Japón", 30,"https://flagcdn.com/w320/jp.png"),
    CountryModel("Francia", 5,"https://flagcdn.com/w320/fr.png"),
)

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()){
    val favoriteNames by viewModel.favoriteNames.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ranking Fifa 2026", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)){
            items(viewModel.countries, key = { it.name }){ country ->
                CountryCard(
                    country = country,
                    isFavorite = favoriteNames.contains(country.name),
                    onFavoriteClick = { viewModel.toggleFavorite(country) }
                )
            }
        }
    }
}

@Composable
fun CountryCard(
    country: CountryModel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
){
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentDescription = country.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(country.imageUrl)
            )
            Spacer(modifier = Modifier.width(12.dp))
            // weight(1f) empuja el botón hasta la derecha
            Column(modifier = Modifier.weight(1f)) {
                Text(country.name, style = MaterialTheme.typography.titleMedium)
                Text("Ranking Fifa: " + country.ranking)
            }
            // Botón a la derecha: guarda / quita de favoritos en Room
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Guardar en favoritos",
                    tint = if (isFavorite) MaterialTheme.colorScheme.error
                           else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
