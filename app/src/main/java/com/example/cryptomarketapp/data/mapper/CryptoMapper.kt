package com.example.cryptomarketapp.data.mapper

import com.example.cryptomarketapp.data.local.CompanyListingEntity
import com.example.cryptomarketapp.data.local.CryptoListingEntity
import com.example.cryptomarketapp.domain.model.CompanyListing
import com.example.cryptomarketapp.domain.model.CryptoListings

fun CryptoListingEntity.toCryptoListing(): CryptoListings {
    return CryptoListings(
        name = name
    )
}

fun CryptoListings.toCryptoListingEntity(): CryptoListingEntity {
    return CryptoListingEntity(
        name = name
    )
}