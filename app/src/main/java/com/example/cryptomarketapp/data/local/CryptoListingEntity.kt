package com.example.cryptomarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoListingEntity(
    val name: String,
    val coinGeckoId: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val high24h: Double?,
    val low24h: Double,
    val priceChangePercentage24: Double,
    @PrimaryKey val id : Int? = null
)
