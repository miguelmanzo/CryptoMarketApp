package com.example.cryptomarketapp.domain.model

data class CryptoListings(
    val name: String,
    val coinGeckoId: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val high24h: Double?,
    val low24h: Double,
    val priceChangePercentage24: Double
)
