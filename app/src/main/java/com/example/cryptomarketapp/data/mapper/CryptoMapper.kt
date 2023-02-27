package com.example.cryptomarketapp.data.mapper

import com.example.cryptomarketapp.data.local.CryptoListingEntity
import com.example.cryptomarketapp.data.remote.dto.CryptoListingResponse
import com.example.cryptomarketapp.domain.model.CryptoListings

fun CryptoListingEntity.toCryptoListing(): CryptoListings {
    return CryptoListings(
        name = name,
        coinGeckoId = coinGeckoId,
        symbol = symbol,
        image = image,
        currentPrice = currentPrice,
        //TODO Change this
        high24h = high24h ?: 1.0,
        low24h = low24h,
        priceChangePercentage24 = priceChangePercentage24

    )
}

fun CryptoListingResponse.toCryptoListingEntity(): CryptoListingEntity {
    return CryptoListingEntity(
        name = name,
        coinGeckoId = coinGeckoId,
        symbol = symbol,
        image = image,
        currentPrice = currentPrice,
        high24h = high24,
        low24h = low24 ?: 1.0,
        priceChangePercentage24 = priceChangePercentage24 ?: 1.0,
    )
}
