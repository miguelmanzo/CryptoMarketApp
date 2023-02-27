package com.example.cryptomarketapp.data.remote.dto

import com.squareup.moshi.Json

data class CryptoListingResponse(
    @field: Json(name = "name") val name : String,
    @field: Json(name = "id") val coinGeckoId : String,
    @field: Json(name = "symbol") val symbol : String,
    @field: Json(name = "image") val image : String,
    @field: Json(name = "current_price") val currentPrice : Double,
    @field: Json(name = "high_24h") val high24 : Double?,
    @field: Json(name = "low_24h") val low24 : Double?,
    @field: Json(name = "price_change_percentage_24h") val priceChangePercentage24 : Double?,
)
