package com.example.cryptomarketapp.presentation.companyListings

import com.example.cryptomarketapp.domain.model.CryptoListings
import com.example.cryptomarketapp.domain.model.FavoriteListings

data class CompanyListingState(
    val companies: List<CryptoListings> = emptyList(),
    val favorites: List<FavoriteListings> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
