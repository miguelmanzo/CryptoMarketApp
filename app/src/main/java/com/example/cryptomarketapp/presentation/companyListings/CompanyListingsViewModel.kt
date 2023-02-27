package com.example.cryptomarketapp.presentation.companyListings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomarketapp.data.local.FavoritesListingEntity
import com.example.cryptomarketapp.domain.model.FavoriteListings
import com.example.cryptomarketapp.domain.repository.StockRepository
import com.example.cryptomarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository,
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())

    private var searchJob: Job? = null

    init {
        getCryptoListings()
    }

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.Refresh -> {
                getCryptoListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCryptoListings()
                }
            }
            is CompanyListingsEvent.OnFavoriteSelection -> {
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
            state = state.copy(favorites = favorites )
        }
    }
}