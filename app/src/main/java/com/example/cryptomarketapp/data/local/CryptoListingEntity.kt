package com.example.cryptomarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoListingEntity(
    val name: String,
    @PrimaryKey val id : Int? = null
)
