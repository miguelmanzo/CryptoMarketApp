package com.example.cryptomarketapp.presentation.cryptoListings

sealed class CryptoListingsEvent {
    object Refresh : CryptoListingsEvent()
    data class OnSearchQueryChange(val query: String) : CryptoListingsEvent()
    data class OnFavoriteSelection(val symbol: String, val isFavorite: Boolean) :
        CryptoListingsEvent()
}
