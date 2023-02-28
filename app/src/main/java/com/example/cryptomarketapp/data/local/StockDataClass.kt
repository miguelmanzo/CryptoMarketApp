package com.example.cryptomarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class, CryptoListingEntity::class, FavoritesListingEntity::class],
    version = 1
)
abstract class StockDataClass : RoomDatabase() {

    abstract val dao: StockDao
}