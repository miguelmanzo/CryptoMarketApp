package com.example.cryptomarketapp.domain.repository

import com.example.cryptomarketapp.domain.model.CompanyInfo
import com.example.cryptomarketapp.domain.model.CompanyListing
import com.example.cryptomarketapp.domain.model.CryptoListings
import com.example.cryptomarketapp.domain.model.IntradayInfo
import com.example.cryptomarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
            fetchFromRemote: Boolean,
            query: String
    ) :  Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol : String
    ) : Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol : String
    ) : Resource<CompanyInfo>

//    suspend fun getCryptoListings(
//        fetchFromRemote: Boolean,
//        query: String
//    ): Flow<Resource<List<CryptoListings>>>
}