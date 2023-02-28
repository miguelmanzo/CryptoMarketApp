package com.example.cryptomarketapp.domain.repository

import com.example.cryptomarketapp.domain.model.*
import com.example.cryptomarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    //TODO Implement later
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>

    suspend fun getCryptoListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CryptoListings>>>

    suspend fun addCryptoToFavorites(isFavorite: String)

    suspend fun removeCryptoFromFavorites(isFavorite: String)

    suspend fun getFavoritesListings(): List<FavoriteListings>
}