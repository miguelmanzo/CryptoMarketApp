package com.example.cryptomarketapp.presentation.cryptoListings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomarketapp.domain.repository.StockRepository
import com.example.cryptomarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListingsViewModel @Inject constructor(
    private val repository: StockRepository,
) : ViewModel() {

    var state by mutableStateOf(CryptoListingState())

    private var searchJob: Job? = null

    init {
        getCryptoListings()
    }

    fun onEvent(event: CryptoListingsEvent) {
        when (event) {
            is CryptoListingsEvent.Refresh -> {
                getCryptoListings(fetchFromRemote = true)
            }
            is CryptoListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCryptoListings()
                }
            }
            is CryptoListingsEvent.OnFavoriteSelection -> {
                searchJob?.cancel()
                viewModelScope.launch(Dispatchers.IO) {
                    if (event.isFavorite) {
                        repository.addCryptoToFavorites(event.symbol)
                    } else {
                        repository.removeCryptoFromFavorites(event.symbol)
                    }
                    getFavoritesListings()
                }

            }
        }
    }

    fun getCryptoListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getCryptoListings(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(
                                    companies = it
                                )
                                getFavoritesListings()
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
        }
    }

    fun getFavoritesListings() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = repository.getFavoritesListings()
            state = state.copy(favorites = favorites)
        }
    }
}