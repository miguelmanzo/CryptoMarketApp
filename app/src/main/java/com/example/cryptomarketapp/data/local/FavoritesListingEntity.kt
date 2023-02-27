package com.example.cryptomarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesListingEntity(
    @PrimaryKey val name: String
)
