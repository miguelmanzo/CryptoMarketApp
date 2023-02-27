package com.example.cryptomarketapp.data.mapper

import com.example.cryptomarketapp.data.local.FavoritesListingEntity
import com.example.cryptomarketapp.domain.model.FavoriteListings

fun FavoritesListingEntity.toFavoriteListing(): FavoriteListings {
    return FavoriteListings(
        name = name,
    )
}

