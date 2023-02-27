package com.example.cryptomarketapp.presentation.cryptoListings

import com.example.cryptomarketapp.domain.model.CryptoListings
import com.example.cryptomarketapp.domain.model.FavoriteListings

data class CryptoListingState(
    val companies: List<CryptoListings> = emptyList(),
    val favorites: List<FavoriteListings> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
